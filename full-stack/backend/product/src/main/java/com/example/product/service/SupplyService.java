package com.example.product.service;

public interface SupplyService {

    void supplyProduct(Long productId, Long warehouseId, int quantity);

    int getQuantityProduct(Long productId);
}
