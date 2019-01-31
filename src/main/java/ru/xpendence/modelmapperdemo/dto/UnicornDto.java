package ru.xpendence.modelmapperdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:54
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnicornDto {

    private Long id;
    private String name;
    private List<DroidDto> droids;
    private String color;
}
