package ru.xpendence.modelmapperdemo.entity;

import lombok.*;
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
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unicorn extends AbstractEntity {

    private String name;
    private List<Droid> droids;
    private Color color;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "unicorn")
    public List<Droid> getDroids() {
        return droids;
    }

    @Column(name = "color")
    public Color getColor() {
        return color;
    }
}
