package ru.xpendence.modelmapperdemo.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:45
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "droids")
@AllArgsConstructor
@NoArgsConstructor
public class Droid extends AbstractEntity {

    private String name;
    private Unicorn unicorn;
    private List<Cupcake> cupcakes;
    private Boolean alive;

    public Droid(String name, Unicorn unicorn, Boolean alive) {
        this.name = name;
        this.unicorn = unicorn;
        this.alive = alive;
    }

    public Droid(String name, Boolean alive) {
        this.name = name;
        this.alive = alive;
    }

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
