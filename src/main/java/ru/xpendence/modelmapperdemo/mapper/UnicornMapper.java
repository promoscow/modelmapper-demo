package ru.xpendence.modelmapperdemo.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.modelmapperdemo.dto.DroidDto;
import ru.xpendence.modelmapperdemo.dto.UnicornDto;
import ru.xpendence.modelmapperdemo.entity.Unicorn;
import ru.xpendence.modelmapperdemo.repository.DroidRepository;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 17:53
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Component
public class UnicornMapper {

    private final ModelMapper mapper;
    private final DroidMapper droidMapper;
    private final DroidRepository droidRepository;

    @Autowired
    public UnicornMapper(ModelMapper mapper, DroidMapper droidMapper, DroidRepository droidRepository) {
        this.mapper = mapper;
        this.droidMapper = droidMapper;
        this.droidRepository = droidRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Unicorn.class, UnicornDto.class)
                .addMappings(m -> m.skip(UnicornDto::setDroids)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UnicornDto.class, Unicorn.class)
                .addMappings(m -> m.skip(Unicorn::setDroids)).setPostConverter(toEntityConverter());
    }

    public UnicornDto toDto(Unicorn unicorn) {
        return mapper.map(unicorn, UnicornDto.class);
    }

    public Unicorn toEntity(UnicornDto dto) {
        return mapper.map(dto, Unicorn.class);
    }

    public Converter<Unicorn, UnicornDto> toDtoConverter() {
        return context -> {
            Unicorn source = context.getSource();
            UnicornDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public Converter<UnicornDto, Unicorn> toEntityConverter() {
        return context -> {
            UnicornDto source = context.getSource();
            Unicorn destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Unicorn source, UnicornDto destination) {
        if (Objects.nonNull(source.getDroids())) {
            destination.setDroids(source.getDroids().stream().map(droidMapper::toDto).collect(Collectors.toList()));
        }
    }

    private void mapSpecificFields(UnicornDto source, Unicorn destination) {
        if (Objects.nonNull(source.getDroids())) {
            destination.setDroids(droidRepository.findAllByIdIn(
                    source.getDroids()
                            .stream()
                            .filter(d -> Objects.nonNull(d.getId()))
                            .map(DroidDto::getId)
                            .collect(Collectors.toList()))
            );
        }
    }
}
