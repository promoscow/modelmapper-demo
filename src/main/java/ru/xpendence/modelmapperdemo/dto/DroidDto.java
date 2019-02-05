package ru.xpendence.modelmapperdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * e-mail: 2262288@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroidDto extends AbstractDto {

    private String name;
    private List<CupcakeDto> cupcakes;
    private Long unicorn;
    private Boolean alive;
}
