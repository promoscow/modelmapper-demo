package ru.xpendence.modelmapperdemo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 17:53
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Component
public class UserMapper {

    @Autowired
    private ModelMapper mapper;
}
