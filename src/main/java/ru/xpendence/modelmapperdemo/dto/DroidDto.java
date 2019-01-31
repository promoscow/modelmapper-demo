package ru.xpendence.modelmapperdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:55
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroidDto {

    private Long id;
    private String name;
    private UnicornDto unicorn;
    private List<CupcakeDto> cupcakes;
    private Boolean alive;
}
