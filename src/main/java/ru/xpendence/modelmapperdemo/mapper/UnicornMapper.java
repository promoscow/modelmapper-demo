package ru.xpendence.modelmapperdemo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xpendence.modelmapperdemo.dto.UnicornDto;
import ru.xpendence.modelmapperdemo.entity.Unicorn;

import javax.annotation.PostConstruct;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 17:53
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Component
public class UnicornMapper {

    @Autowired
    private ModelMapper mapper;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Unicorn.class, UnicornDto.class)
                .addMappings(m -> {
                    m.skip();
                })
    }
}
