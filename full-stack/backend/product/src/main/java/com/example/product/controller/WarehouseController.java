package com.example.product.controller;

import com.example.product.dto.WarehouseDto;
import com.example.product.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private WarehouseService warehouseService;

    @PostMapping("/createWarehouse")
    public ResponseEntity<WarehouseDto> createWarehouse(@RequestBody WarehouseDto warehouse) {

        return new ResponseEntity<>(warehouseService.createWarehouse(warehouse), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<WarehouseDto>> getWarehouses() {

        return new ResponseEntity<>(warehouseService.getWarehouses(), HttpStatus.OK);
    }
}
