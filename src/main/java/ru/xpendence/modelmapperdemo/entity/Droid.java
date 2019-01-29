package ru.xpendence.modelmapperdemo.entity;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:45
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "droids")
public class Droid extends AbstractEntity {

    private String name;
    private Unicorn unicorn;
    private List<Cupcake> cupcakes;
    private Boolean alive;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unicorn_id")
    public Unicorn getUnicorn() {
        return unicorn;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "droid")
    public List<Cupcake> getCupcakes() {
        return cupcakes;
    }

    @Column(name = "alive")
    public Boolean getAlive() {
        return alive;
    }
}
