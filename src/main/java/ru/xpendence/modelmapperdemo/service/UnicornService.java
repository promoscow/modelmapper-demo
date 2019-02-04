package ru.xpendence.modelmapperdemo.service;

import ru.xpendence.modelmapperdemo.dto.UnicornDto;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 01.02.19
 * e-mail: 2262288@gmail.com
 */
public interface UnicornService {

    UnicornDto save(UnicornDto dto);

    UnicornDto get(Long id);
}
