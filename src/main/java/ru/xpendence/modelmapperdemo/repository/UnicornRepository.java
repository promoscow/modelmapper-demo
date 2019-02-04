package ru.xpendence.modelmapperdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpendence.modelmapperdemo.entity.Unicorn;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 01.02.19
 * e-mail: 2262288@gmail.com
 */
@Repository
public interface UnicornRepository extends JpaRepository<Unicorn, Long> {
}
