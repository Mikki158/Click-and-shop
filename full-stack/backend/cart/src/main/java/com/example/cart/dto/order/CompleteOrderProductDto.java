package com.example.cart.dto.order;

import com.example.cart.dto.AbstractDto;
import lombok.Data;

@Data
public class CompleteOrderProductDto extends AbstractDto {

    private Long productId;
    private int quantity;
    private Long price;

    private Long completeOrderId;
}
