package com.example.product.mapper;

import com.example.product.dto.AbstractDto;
import com.example.product.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);
    D toDto(E entity);
}