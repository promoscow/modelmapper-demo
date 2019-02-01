package ru.xpendence.modelmapperdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpendence.modelmapperdemo.entity.Cupcake;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 01.02.19
 * Time: 17:16
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Repository
public interface CupcakeRepository extends JpaRepository<Cupcake, Long> {
}
