package ru.xpendence.modelmapperdemo.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xpendence.modelmapperdemo.attributes.Color;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:44
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Entity
@Table(name = "unicorns")
@EqualsAndHashCode(callSuper = false)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unicorn extends AbstractEntity {

    private String name;
    private List<Droid> droids;
    private Color color;

    public Unicorn(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "unicorn")
    public List<Droid> getDroids() {
        return droids;
    }

    @Column(name = "color")
    public Color getColor() {
        return color;
    }
}
