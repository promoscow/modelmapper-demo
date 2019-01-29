package ru.xpendence.modelmapperdemo.dto;

import java.util.List;
import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:54
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public class UnicornDto {

    private Long id;
    private String name;
    private List<DroidDto> droids;
    private String color;

    public UnicornDto() {
    }

    public UnicornDto(Long id, String name, List<DroidDto> droids, String color) {
        this.id = id;
        this.name = name;
        this.droids = droids;
        this.color = color;
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

    public List<DroidDto> getDroids() {
        return droids;
    }

    public void setDroids(List<DroidDto> droids) {
        this.droids = droids;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnicornDto that = (UnicornDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(droids, that.droids) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, droids, color);
    }
}
