package ru.xpendence.modelmapperdemo.entity;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import ru.xpendence.modelmapperdemo.attributes.Filling;

import javax.persistence.*;
import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:45
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Entity
@Table(name = "cupcakes")
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Cupcake extends AbstractEntity {

    private Filling filling;
    private Droid droid;

    @Column(name = "filling")
    public Filling getFilling() {
        return filling;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "droid_id")
    public Droid getDroid() {
        return droid;
    }
}
