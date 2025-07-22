package com.example.product.dto;

import lombok.Data;

@Data
public class SupplyRequest {

    private Long productId;
    private Long warehouseId;
    private int quantity;
}
