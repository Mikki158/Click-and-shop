package com.example.cart.service;

import com.example.cart.dto.order.CompleteOrderDto;
import com.example.cart.dto.order.CompleteOrderProductDto;
import com.example.cart.dto.order.OrderDto;
import com.example.cart.dto.order.OrderProductDto;
import com.example.cart.entity.order.CompleteOrder;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(Long userId, Long pickupPointId, List<OrderProductDto> products);

    List<OrderDto> getOrders(Long userId);

    OrderDto getOrder(Long orderId);

    void setOrderStatus(Long orderId, String status);

    List<OrderProductDto> getProducts(Long orderId);


}
