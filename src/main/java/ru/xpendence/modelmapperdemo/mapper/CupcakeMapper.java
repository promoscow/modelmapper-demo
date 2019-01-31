package ru.xpendence.modelmapperdemo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.modelmapperdemo.dto.CupcakeDto;
import ru.xpendence.modelmapperdemo.entity.Cupcake;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 30.01.19
 * Time: 18:21
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Component
public class CupcakeMapper {

    private final ModelMapper mapper;

    @Autowired
    public CupcakeMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CupcakeDto toDto(Cupcake cupcake) {
        return mapper.map(cupcake, CupcakeDto.class);
    }
}
