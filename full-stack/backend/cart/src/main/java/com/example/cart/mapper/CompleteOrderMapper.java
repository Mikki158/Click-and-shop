package com.example.cart.mapper;

import com.example.cart.dto.order.CompleteOrderDto;
import com.example.cart.entity.order.CompleteOrder;
import org.springframework.stereotype.Component;

@Component
public class CompleteOrderMapper extends AbstractMapper<CompleteOrder, CompleteOrderDto> {

    public CompleteOrderMapper() {
        super(CompleteOrder.class, CompleteOrderDto.class);
    }
}
