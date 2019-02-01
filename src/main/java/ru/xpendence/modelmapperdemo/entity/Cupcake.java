package ru.xpendence.modelmapperdemo.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xpendence.modelmapperdemo.attributes.Filling;

import javax.persistence.*;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 13:45
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Entity
@Table(name = "cupcakes")
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
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

    public Cupcake(Filling filling) {
        this.filling = filling;
    }
}
