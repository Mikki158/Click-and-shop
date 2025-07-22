package com.example.product.service;

import com.example.product.dto.WarehouseDto;

import java.util.List;

public interface WarehouseService {

    WarehouseDto createWarehouse(WarehouseDto warehouse);

    List<WarehouseDto> getWarehouses();
}
