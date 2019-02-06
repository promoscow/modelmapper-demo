package ru.xpendence.modelmapperdemo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.modelmapperdemo.dto.CupcakeDto;
import ru.xpendence.modelmapperdemo.entity.Cupcake;
import ru.xpendence.modelmapperdemo.repository.DroidRepository;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 30.01.19
 * e-mail: 2262288@gmail.com
 */
@Component
public class CupcakeMapper extends AbstractMapper<Cupcake, CupcakeDto> {

    private final ModelMapper mapper;
    private final DroidRepository droidRepository;

    @Autowired
    public CupcakeMapper(ModelMapper mapper, DroidRepository droidRepository) {
        super(Cupcake.class, CupcakeDto.class);
        this.mapper = mapper;
        this.droidRepository = droidRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Cupcake.class, CupcakeDto.class)
                .addMappings(m -> m.skip(CupcakeDto::setDroidId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(CupcakeDto.class, Cupcake.class)
                .addMappings(m -> m.skip(Cupcake::setDroid)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Cupcake source, CupcakeDto destination) {
        destination.setDroidId(getId(source));
    }

    private Long getId(Cupcake source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getDroid().getId();
    }

    @Override
    void mapSpecificFields(CupcakeDto source, Cupcake destination) {
        destination.setDroid(droidRepository.findById(source.getDroidId()).orElse(null));
    }
}
