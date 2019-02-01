package ru.xpendence.modelmapperdemo.service;

import ru.xpendence.modelmapperdemo.dto.UnicornDto;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 01.02.19
 * Time: 19:00
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public interface UnicornService {

    UnicornDto save(UnicornDto dto);

    UnicornDto get(Long id);
}
