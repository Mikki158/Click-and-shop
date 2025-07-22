package com.example.product.mapper;

import com.example.product.dto.WarehouseDto;
import com.example.product.entity.Warehouse;
import com.example.product.repository.WarehouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper extends AbstractMapper<Warehouse, WarehouseDto>{

    public WarehouseMapper() {
        super(Warehouse.class, WarehouseDto.class);
    }
}
