package com.example.cart.service;

import com.example.cart.dto.order.CompleteOrderDto;
import com.example.cart.dto.order.CompleteOrderProductDto;

import java.util.List;

public interface CompleteOrderService {

    List<CompleteOrderDto> getCompleteOrders(Long userId);

    List<CompleteOrderProductDto> getCompleteProduct(Long completeOrderId);

    CompleteOrderDto getCompleteOrder(Long completeOrderId);

    List<Long> getAllCompleteProducts(Long userId);


}
