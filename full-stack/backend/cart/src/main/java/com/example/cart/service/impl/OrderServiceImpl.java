package com.example.cart.service.impl;

import com.example.cart.dto.order.CompleteOrderDto;
import com.example.cart.dto.order.CompleteOrderProductDto;
import com.example.cart.dto.order.OrderDto;
import com.example.cart.dto.order.OrderProductDto;
import com.example.cart.entity.order.CompleteOrder;
import com.example.cart.entity.order.CompleteOrderProuct;
import com.example.cart.entity.order.Order;
import com.example.cart.entity.order.OrderProduct;
import com.example.cart.mapper.CompleteOrderMapper;
import com.example.cart.mapper.CompleteOrderProductMapper;
import com.example.cart.mapper.OrderMapper;
import com.example.cart.mapper.OrderProductMapper;
import com.example.cart.repository.order.CompleteOrderProductRepository;
import com.example.cart.repository.order.CompleteOrderRepository;
import com.example.cart.repository.order.OrderProductRepository;
import com.example.cart.repository.order.OrderRepository;
import com.example.cart.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    OrderProductRepository orderProductRepository;
    OrderProductMapper orderProductMapper;
    OrderMapper orderMapper;
    CompleteOrderRepository completeOrderRepository;
    CompleteOrderProductRepository completeOrderProductRepository;


    @Override
    public OrderDto createOrder(Long userId, Long pickupPointId, List<OrderProductDto> products) {

        if (!existsPickupPoint(pickupPointId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пункт выдачи не найден");

        if (!checkStock(products))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не все товары есть в наличии");

        Order order = orderRepository.save(new Order(userId, pickupPointId));

        for (OrderProductDto product : products) {
            order.setTotalPrice(order.getTotalPrice() + product.getPrice() * product.getQuantity());
            product.setOrderId(order.getId());
            orderProductRepository.save(orderProductMapper.toEntity(product));
        }

        orderRepository.save(order);

        return orderMapper.toDto(order);
    }
    
    private boolean existsPickupPoint(Long pickupPointId) {
        return true;
    }

    private boolean checkStock(List<OrderProductDto> products) {
        return true;
    }

    @Override
    public List<OrderDto> getOrders(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDto> response = new ArrayList<>();

        for (Order order : orders) {
            response.add(orderMapper.toDto(order));
        }

        return response;
    }

    @Override
    public OrderDto getOrder(Long orderId) {

        return orderMapper.toDto(orderRepository.getReferenceById(orderId));
    }

    @Override
    public void setOrderStatus(Long orderId, String status) {

        Order order = orderRepository.getReferenceById(orderId);

        order.setStatus(status);

        if (status.equals("Выдан") || status.equals("Отменен")) {

            CompleteOrder completeOrder = completeOrderRepository.save(new CompleteOrder(
                    order.getUserId(),
                    status,
                    order.getTotalPrice(),
                    order.getPickupPointId()
            ));

            List<OrderProduct> products = orderProductRepository.getOrderProductsByOrder(order);

            for (OrderProduct product : products) {
                CompleteOrderProuct completeOrderProuct = new CompleteOrderProuct(
                        product.getProductId(),
                        product.getQuantity(),
                        product.getPrice(),
                        completeOrder);

                completeOrderProductRepository.save(completeOrderProuct);
                orderProductRepository.delete(product);
            }

            orderRepository.delete(order);
        }
    }

    @Override
    public List<OrderProductDto> getProducts(Long orderId) {

        Order order = orderRepository.getReferenceById(orderId);

        List<OrderProduct> products = orderProductRepository.getOrderProductsByOrder(order);
        List<OrderProductDto> response = new ArrayList<>();

        for (OrderProduct product : products) {
            response.add(orderProductMapper.toDto(product));
        }

        return response;
    }
}
