package org.mapper.abstractMapper.mapper;

import org.mapper.abstractMapper.dto.AbstractDto;
import org.mapper.abstractMapper.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);
    D toDto(E entity);
}