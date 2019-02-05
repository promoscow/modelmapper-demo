package ru.xpendence.modelmapperdemo.dto;

import lombok.*;

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
@ToString
public class UnicornDto extends AbstractDto {

    private String name;
    private List<DroidDto> droids;
    private String color;
}
