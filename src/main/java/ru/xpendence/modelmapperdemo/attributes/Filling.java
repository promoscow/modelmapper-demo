package ru.xpendence.modelmapperdemo.attributes;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:48
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
public enum Filling {
    CHOCOLATE(0, "Chocolate"),
    CHERRY(1, "Cherry"),
    RASPBERRY(5, "Raspberry");

    Filling(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    private Integer id;
    private String type;

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }}




