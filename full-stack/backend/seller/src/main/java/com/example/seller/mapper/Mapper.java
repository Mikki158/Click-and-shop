package com.example.seller.mapper;

import com.example.seller.dto.AbstractDto;
import com.example.seller.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);
    D toDto(E entity);
}