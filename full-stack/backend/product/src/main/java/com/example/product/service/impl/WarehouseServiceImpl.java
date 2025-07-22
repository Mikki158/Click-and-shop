package com.example.product.service.impl;

import com.example.product.dto.WarehouseDto;
import com.example.product.entity.Warehouse;
import com.example.product.mapper.WarehouseMapper;
import com.example.product.repository.WarehouseRepository;
import com.example.product.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    WarehouseMapper mapper;
    WarehouseRepository repository;

    public WarehouseDto createWarehouse(WarehouseDto warehouseDto) {

        Warehouse warehouse = mapper.toEntity(warehouseDto);

        Warehouse saveWarehouse = repository.save(warehouse);

        return mapper.toDto(saveWarehouse);
    }

    @Override
    public List<WarehouseDto> getWarehouses() {

        List<WarehouseDto> response = new ArrayList<>();

        List<Warehouse> warehouses = repository.findAll();

        for (Warehouse warehouse : warehouses) {
            response.add(mapper.toDto(warehouse));
        }

        return response;
    }
}
