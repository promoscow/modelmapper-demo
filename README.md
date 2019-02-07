# ModelMapper: путешествие туда и обратно
Этот проект создавался как тестовый для статьи на Хабре. Текст статьи: https://habr.com/ru/post/438808/

## Основные тезисы статьи.

Первый шаг — это, конечно, добавление зависимости. Я использую gradle, но вам не составит труда добавить зависимость в maven-проект.

    compile group: 'org.modelmapper', name: 'modelmapper', version: '2.3.2'

Этого достаточно, чтобы маппер заработал. Далее, нам необходимо создать бин.

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }
    
Обычно достаточно просто вернуть new ModelMapper, но не лишним будет настроить маппер для наших нужд. Я задал строгую стратегию соответствия, включил сопоставление полей, пропуск нулловых полей и задал приватный уровень доступа к полям.

Далее, создаём следующую структуру сущностей. У нас будет единорог (Unicorn), у которого в подчинении будет какое-то количество дроидов (Droid), и у каждого дроида будет какое-то количество капкейков (Cupcake).

Эти сущности мы будем конвертировать в DTO. Существует как минимум два подхода к конвертации зависимостей из сущности в DTO. Один подразумевает сохранение только ID вместо сущности, но тогда каждую сущность из зависимости при нужде мы будем дёргать по ID дополнительно. Второй подход подразумевает сохранение DTO в зависимости. Так, при первом подходе мы бы конвертировали List droids в List droids (в новом списке храним только ID), а при втором подходе мы будем сохранять в List droids.

Для тонкой настройки маппера под наши нужды нам будет необходимо создать собственный класс-обёртку и переопределить логику для маппинга коллекций. Для этого мы создаём класс-компонент UnicornMapper, автовайрим туда наш маппер и переопределяем нужные нам методы.

Самый простой вариант класса-обёртки выглядит так:

    @Component
    public class UnicornMapper {
    
        @Autowired
        private ModelMapper mapper;
    
        @Override
        public Unicorn toEntity(UnicornDto dto) {
            return Objects.isNull(dto) ? null : mapper.map(dto, Unicorn.class);
        }
    
        @Override
        public UnicornDto toDto(Unicorn entity) {
            return Objects.isNull(entity) ? null : mapper.map(entity, UnicornDto.class);
        }
    }

Теперь нам достаточно заавтовайрить наш маппер в какой-нибудь сервис и дёргать по методам toDto и toEntity. Найденные в объекте сущности маппер будет превращать в DTO, DTO — в сущности.

    @Service
    public class UnicornServiceImpl implements UnicornService {
    
        private final UnicornRepository repository;
        private final UnicornMapper mapper;
    
        @Autowired
        public UnicornServiceImpl(UnicornRepository repository, UnicornMapper mapper) {
            this.repository = repository;
            this.mapper = mapper;
        }
    
        @Override
        public UnicornDto save(UnicornDto dto) {
            return mapper.toDto(repository.save(mapper.toEntity(dto)));
        }
    
        @Override
        public UnicornDto get(Long id) {
            return mapper.toDto(repository.getOne(id));
        }
    }

Но если мы попробуем таким образом законвертировать что-нибудь, а потом вызвать, к примеру, toString, то мы получим StackOverflowException, и вот почему: в UnicornDto находится список DroidDto, в котором находится UnicornDto, в котором находятся DroidDto, и так до того момента, пока не закончится стековая память. Поэтому для обратных зависимостей я обычно использую не UnicornDto unicorn, а Long unicornId. Мы, таким образом, сохраняем связь с Unicorn, но обрубаем циклическую зависимость. Поправим наши DTO таким образом, чтобы вместо обратных DTO они хранили ID своих зависимостей.

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class DroidDto extends AbstractDto {
    ...
        //private UnicornDto unicorn;
        private Long unicornId;
    ...
    }

и так далее.

Но теперь, если мы вызовём DroidMapper, мы получим unicornId == null. Это происходит потому, что ModelMapper не может определить точно, что такое Long. И просто не сетит его. И нам придётся заняться тонкой настройкой необходимых мапперов, чтобы научить их мапить сущности в ID.

Вспоминаем, что с каждым бином после его инициализации можно поработать вручную.

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Droid.class, DroidDto.class)
                .addMappings(m -> m.skip(DroidDto::setUnicornId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(DroidDto.class, Droid.class)
                .addMappings(m -> m.skip(Droid::setUnicorn)).setPostConverter(toEntityConverter());
    }

В @PostConstruct мы зададим правила, в которых укажем, какие поля маппер трогать не должен, потому что для них мы определим логику самостоятельно. В нашем случае, это как определение unicornId в DTO, так и определение Unicorn в сущности (поскольку что делать с Long unicornId, маппер так же не знает).

TypeMap — это и есть правило, в котором мы указываем все нюансы маппинга, а также, задаём конвертер. Мы указали, что для конвертирования из Droid в DroidDto мы пропускаем setUnicornId, а при обратной конвертации — setUnicorn. Конвертировать мы всё будем в конвертере toDtoConverter() для UnicornDto и в toEntityConverter() для Unicorn. Эти конвертеры мы должны описать в нашем компоненте.

Самый простой постконвертер выглядит так:

    Converter<UnicornDto, Unicorn> toEntityConverter() {
        return MappingContext::getDestination;
    }

Нам необходимо расширить его функциональность:

     public Converter<UnicornDto, Unicorn> toEntityConverter() {
        return context -> {
            UnicornDto source = context.getSource();
            Unicorn destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

То же самое делаем и с обратным конвертером:

    public Converter<Unicorn, UnicornDto> toDtoConverter() {
        return context -> {
            Unicorn source = context.getSource();
            UnicornDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

По сути, мы просто вставляем в каждый постконвертер дополнительный метод, в котором пропишем собственную логику для пропущенных полей.

    public void mapSpecificFields(Droid source, DroidDto destination) {
        destination.setUnicornId(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getUnicorn().getId());
    }

    void mapSpecificFields(DroidDto source, Droid destination) {
        destination.setUnicorn(unicornRepository.findById(source.getUnicornId()).orElse(null));
    }

При мапинге в DTO мы сетим ID сущности. При мапинге в DTO достаём сущность из репозитория по ID.

И всё.

## Повышаем уровень абстракции.

Для начала, определим интерфейс для основных методов класса-обёртки.

    public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {
    
        E toEntity(D dto);
            
        D toDto(E entity);
    }

Унаследуем от него абстрактный класс.

    public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto>     implements Mapper<E, D> {
    
        @Autowired
        ModelMapper mapper;
    
        private Class<E> entityClass;
        private Class<D> dtoClass;
    
        AbstractMapper(Class<E> entityClass, Class<D> dtoClass) {
            this.entityClass = entityClass;
            this.dtoClass = dtoClass;
        }
    
        @Override
        public E toEntity(D dto) {
            return Objects.isNull(dto)
                    ? null
                    : mapper.map(dto, entityClass);
        }
    
        @Override
        public D toDto(E entity) {
            return Objects.isNull(entity)
                    ? null
                    : mapper.map(entity, dtoClass);
        }
    
        Converter<E, D> toDtoConverter() {
            return context -> {
                E source = context.getSource();
                D destination = context.getDestination();
                mapSpecificFields(source, destination);
                return context.getDestination();
            };
        }
    
        Converter<D, E> toEntityConverter() {
            return context -> {
                D source = context.getSource();
                E destination = context.getDestination();
                mapSpecificFields(source, destination);
                return context.getDestination();
            };
        }
    
        void mapSpecificFields(E source, D destination) {
        }
    
        void mapSpecificFields(D source, E destination) {
        }
    }

Постконвертеры и методы заполнения специфичных полей смело отправляем туда. Также, создаём два объекта типа Class и конструктор для их инициализации:

    private Class<E> entityClass;
    private Class<D> dtoClass;

    AbstractMapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

Теперь количество кода в DroidMapper сокращается до следующего:

    @Component
    public class DroidMapper extends AbstractMapper<Droid, DroidDto> {
    
        private final ModelMapper mapper;
        private final UnicornRepository unicornRepository;
    
        @Autowired
        public DroidMapper(ModelMapper mapper, UnicornRepository unicornRepository) {
            super(Droid.class, DroidDto.class);
            this.mapper = mapper;
            this.unicornRepository = unicornRepository;
        }
    
        @PostConstruct
        public void setupMapper() {
            mapper.createTypeMap(Droid.class, DroidDto.class)
                    .addMappings(m -> m.skip(DroidDto::setUnicornId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(DroidDto.class, Droid.class)
                    .addMappings(m -> m.skip(Droid::setUnicorn)).setPostConverter(toEntityConverter());
        }
    
        @Override
        public void mapSpecificFields(Droid source, DroidDto destination) {
            destination.setUnicornId(getId(source));
        }
    
        private Long getId(Droid source) {
            return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getUnicorn().getId();
        }
    
        @Override
        void mapSpecificFields(DroidDto source, Droid destination) {
            destination.setUnicorn(unicornRepository.findById(source.getUnicornId()).orElse(null));
        }
    }
 
Маппер без специфичных полей выглядит вообще просто:

    @Component
    public class UnicornMapper extends AbstractMapper<Unicorn, UnicornDto> {
    
        @Autowired
        public UnicornMapper() {
            super(Unicorn.class, UnicornDto.class);
        }
    }
