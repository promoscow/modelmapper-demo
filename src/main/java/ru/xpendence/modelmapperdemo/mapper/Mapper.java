package ru.xpendence.modelmapperdemo.mapper;

import ru.xpendence.modelmapperdemo.dto.AbstractDto;
import ru.xpendence.modelmapperdemo.entity.AbstractEntity;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 04.02.19
 * Time: 21:53
 * e-mail: 2262288@gmail.com
 */
public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
