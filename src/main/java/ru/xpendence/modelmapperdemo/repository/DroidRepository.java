package ru.xpendence.modelmapperdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpendence.modelmapperdemo.entity.Droid;

import java.util.List;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 01.02.19
 * e-mail: 2262288@gmail.com
 */
@Repository
public interface DroidRepository extends JpaRepository<Droid, Long> {

    List<Droid> findAllByIdIn(List<Long> ids);
}
