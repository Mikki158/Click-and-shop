package com.example.AuthTG.mapper;


import com.example.AuthTG.dto.AbstractDto;
import com.example.AuthTG.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);
    D toDto(E entity);
}