package ru.xpendence.modelmapperdemo.entity;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 29.01.19
 * Time: 17:08
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@MappedSuperclass
@Setter
@EqualsAndHashCode
@ToString
public abstract class AbstractEntity implements Serializable {

    Long id;
    LocalDateTime created;
    LocalDateTime updated;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Column(name = "created", updatable = false)
    public LocalDateTime getCreated() {
        return created;
    }

    @Column(name = "updated", insertable = false)
    public LocalDateTime getUpdated() {
        return updated;
    }

    @PrePersist
    public void toCreate() {
        setCreated(LocalDateTime.now());
    }

    @PreUpdate
    public void toUpdate() {
        setUpdated(LocalDateTime.now());
    }
}
