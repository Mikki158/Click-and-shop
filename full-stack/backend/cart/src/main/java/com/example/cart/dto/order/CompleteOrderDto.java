package com.example.cart.dto.order;

import com.example.cart.dto.AbstractDto;
import lombok.Data;

@Data
public class CompleteOrderDto extends AbstractDto {

    private Long userId;
    private String status;
    private Long totalPrice;
    private Long pickupPointId;
}
