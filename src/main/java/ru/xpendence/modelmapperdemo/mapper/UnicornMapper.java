package ru.xpendence.modelmapperdemo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.modelmapperdemo.dto.UnicornDto;
import ru.xpendence.modelmapperdemo.entity.Unicorn;
import ru.xpendence.modelmapperdemo.repository.DroidRepository;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * e-mail: 2262288@gmail.com
 */
@Component
public class UnicornMapper extends AbstractMapper<Unicorn, UnicornDto> {

    private final DroidMapper droidMapper;
    private final DroidRepository droidRepository;

    @Autowired
    public UnicornMapper(DroidMapper droidMapper, DroidRepository droidRepository) {
        super(Unicorn.class, UnicornDto.class);
        this.droidMapper = droidMapper;
        this.droidRepository = droidRepository;
    }

//    @PostConstruct
//    public void setupMapper() {
//        mapper.createTypeMap(Unicorn.class, UnicornDto.class)
//                .addMappings(m -> m.skip(UnicornDto::setDroids)).setPostConverter(toDtoConverter());
//        mapper.createTypeMap(UnicornDto.class, Unicorn.class)
//                .addMappings(m -> m.skip(Unicorn::setDroids)).setPostConverter(toEntityConverter());
//    }

//    @Override
//    void mapSpecificFields(Unicorn source, UnicornDto destination) {
//        if (Objects.nonNull(source.getDroids())) {
//            destination.setDroids(source.getDroids().stream().map(droidMapper::toDto).collect(Collectors.toList()));
//        }
//    }
//
//    @Override
//    void mapSpecificFields(UnicornDto source, Unicorn destination) {
//        if (Objects.nonNull(source.getDroids())) {
//            destination.setDroids(droidRepository.findAllByIdIn(getIds(source.getDroids())));
//        }
//    }
}
