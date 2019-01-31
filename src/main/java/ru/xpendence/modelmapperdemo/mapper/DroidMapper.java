package ru.xpendence.modelmapperdemo.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.modelmapperdemo.dto.DroidDto;
import ru.xpendence.modelmapperdemo.entity.Droid;

import javax.annotation.PostConstruct;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 30.01.19
 * Time: 18:19
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Component
public class DroidMapper {

    private final ModelMapper mapper;

    @Autowired
    public DroidMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Droid.class, DroidDto.class)
                .addMappings(m -> m.skip(DroidDto::setCupcakes)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(DroidDto.class, Droid.class)
                .addMappings(m -> m.skip(Droid::setCupcakes)).setPostConverter(toEntityConverter());
    }

    private Converter<Droid, DroidDto> toDtoConverter() {
        return MappingContext::getDestination;
    }

    private Converter<DroidDto, Droid> toEntityConverter() {
        return MappingContext::getDestination;
    }
}
