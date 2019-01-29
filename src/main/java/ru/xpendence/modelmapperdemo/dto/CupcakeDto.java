package ru.xpendence.modelmapperdemo.dto;

import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:55
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public class CupcakeDto {

    private Long id;
    private String name;
    private Integer filling;

    public CupcakeDto() {
    }

    public CupcakeDto(Long id, String name, Integer filling) {
        this.id = id;
        this.name = name;
        this.filling = filling;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFilling() {
        return filling;
    }

    public void setFilling(Integer filling) {
        this.filling = filling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CupcakeDto that = (CupcakeDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(filling, that.filling);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, filling);
    }
}
