package ru.xpendence.modelmapperdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xpendence.modelmapperdemo.dto.UnicornDto;
import ru.xpendence.modelmapperdemo.mapper.UnicornMapper;
import ru.xpendence.modelmapperdemo.repository.UnicornRepository;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 01.02.19
 * Time: 19:01
 * e-mail: vyacheslav.chernyshov@stoloto.ru
 */
@Service
public class UnicornServiceImpl implements UnicornService {

    private final UnicornRepository repository;
    private final UnicornMapper mapper;

    @Autowired
    public UnicornServiceImpl(UnicornRepository repository, UnicornMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UnicornDto save(UnicornDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public UnicornDto get(Long id) {
        return mapper.toDto(repository.getOne(id));
    }
}
