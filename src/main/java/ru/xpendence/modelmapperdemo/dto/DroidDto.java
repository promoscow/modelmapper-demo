package ru.xpendence.modelmapperdemo.dto;

import java.util.List;
import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:55
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public class DroidDto {

    private Long id;
    private String name;
    private UnicornDto unicorn;
    private List<CupcakeDto> cupcakes;
    private Boolean alive;

    public DroidDto() {
    }

    public DroidDto(Long id, String name, UnicornDto unicorn, List<CupcakeDto> cupcakes, Boolean alive) {
        this.id = id;
        this.name = name;
        this.unicorn = unicorn;
        this.cupcakes = cupcakes;
        this.alive = alive;
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

    public UnicornDto getUnicorn() {
        return unicorn;
    }

    public void setUnicorn(UnicornDto unicorn) {
        this.unicorn = unicorn;
    }

    public List<CupcakeDto> getCupcakes() {
        return cupcakes;
    }

    public void setCupcakes(List<CupcakeDto> cupcakes) {
        this.cupcakes = cupcakes;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroidDto droidDto = (DroidDto) o;
        return Objects.equals(id, droidDto.id) &&
                Objects.equals(name, droidDto.name) &&
                Objects.equals(unicorn, droidDto.unicorn) &&
                Objects.equals(cupcakes, droidDto.cupcakes) &&
                Objects.equals(alive, droidDto.alive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unicorn, cupcakes, alive);
    }
}
