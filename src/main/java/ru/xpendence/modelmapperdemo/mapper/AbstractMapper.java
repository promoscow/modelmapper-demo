package ru.xpendence.modelmapperdemo.mapper;

import lombok.Setter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.xpendence.modelmapperdemo.dto.AbstractDto;
import ru.xpendence.modelmapperdemo.entity.AbstractEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 04.02.19
 * Time: 21:55
 * e-mail: 2262288@gmail.com
 */
@Setter
public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> implements Mapper<E, D> {

    @Autowired
    ModelMapper mapper;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    AbstractMapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, dtoClass);
    }

    Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    void mapSpecificFields(E source, D destination) {
    }

    void mapSpecificFields(D source, E destination) {
    }

    List<Long> getIds(List<? extends AbstractDto> sourceList) {
        return sourceList
                .stream()
                .filter(s -> Objects.nonNull(s.getId()))
                .map(AbstractDto::getId)
                .collect(Collectors.toList());
    }
}
