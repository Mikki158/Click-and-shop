package com.example.product.service.impl;

import com.example.product.entity.Supply;
import com.example.product.repository.ProductRepository;
import com.example.product.repository.SupplyRepository;
import com.example.product.repository.WarehouseRepository;
import com.example.product.service.SupplyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupplyServiceImpl implements SupplyService {

    SupplyRepository supplyRepository;
    ProductRepository productRepository;
    WarehouseRepository warehouseRepository;

    @Override
    public void supplyProduct(Long productId, Long warehouseId, int quantity) {

        Supply supply = supplyRepository
                .findByProductIdAndWarehouseId(productId, warehouseId)
                .orElse(new Supply(productRepository.getReferenceById(productId),
                        warehouseRepository.getReferenceById(warehouseId),
                        0));

        supply.setQuantity(supply.getQuantity() + quantity);
        supplyRepository.save(supply);
    }

    @Override
    public int getQuantityProduct(Long productId) {
        List<Supply> supplies = supplyRepository.findByProductId(productId);

        int quantity = 0;

        for (Supply supply : supplies) {
            quantity += supply.getQuantity();
        }

        return quantity;
    }
}
