package com.example.cart.dto.order;

import com.example.cart.dto.AbstractDto;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto extends AbstractDto {

    private Long pickupPointId;
    List<OrderProductDto> products;
}
