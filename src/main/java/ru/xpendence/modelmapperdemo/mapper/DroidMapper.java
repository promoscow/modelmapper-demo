package ru.xpendence.modelmapperdemo.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.modelmapperdemo.dto.DroidDto;
import ru.xpendence.modelmapperdemo.entity.Droid;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 30.01.19
 * Time: 18:19
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Component
public class DroidMapper {

    private final ModelMapper mapper;
    private final CupcakeMapper cupcakeMapper;

    @Autowired
    public DroidMapper(ModelMapper mapper, CupcakeMapper cupcakeMapper) {
        this.mapper = mapper;
        this.cupcakeMapper = cupcakeMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Droid.class, DroidDto.class)
                .addMappings(m -> m.skip(DroidDto::setCupcakes)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(DroidDto.class, Droid.class)
                .addMappings(m -> m.skip(Droid::setCupcakes)).setPostConverter(toEntityConverter());
    }

    public DroidDto toDto(Droid droid) {
        return mapper.map(droid, DroidDto.class);
    }

    private Converter<Droid, DroidDto> toDtoConverter() {
        return context -> {
            Droid source = context.getSource();
            DroidDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Droid source, DroidDto destination) {
        destination.setCupcakes(source.getCupcakes().stream().map(cupcakeMapper::toDto).collect(Collectors.toList()));
    }

    private Converter<DroidDto, Droid> toEntityConverter() {
        return MappingContext::getDestination;
    }
}
