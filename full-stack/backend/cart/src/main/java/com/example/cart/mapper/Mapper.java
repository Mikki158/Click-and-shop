package com.example.cart.mapper;

import com.example.cart.dto.AbstractDto;
import com.example.cart.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);
    D toDto(E entity);
}
