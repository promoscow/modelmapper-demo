package ru.xpendence.modelmapperdemo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 17:12
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Data
public abstract class AbstractDto implements Serializable {

    Long id;
    LocalDateTime created;
    LocalDateTime updated;
}
