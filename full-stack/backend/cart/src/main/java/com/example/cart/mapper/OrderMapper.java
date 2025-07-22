package com.example.cart.mapper;

import com.example.cart.dto.order.OrderDto;
import com.example.cart.entity.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends AbstractMapper<Order, OrderDto> {

    public OrderMapper() {
        super(Order.class, OrderDto.class);
    }
}
