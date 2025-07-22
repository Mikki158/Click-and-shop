package com.example.cart.controller;

import com.example.cart.dto.order.*;
import com.example.cart.dto.UserDto;
import com.example.cart.service.AuthService;
import com.example.cart.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    OrderService orderservice;
    AuthService authService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateOrderDto createOrderDto) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        return new ResponseEntity<>(orderservice.createOrder(userId,
                createOrderDto.getPickupPointId(), createOrderDto.getProducts()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        return new ResponseEntity<>(orderservice.getOrders(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable("id") Long orderId) {

        return new ResponseEntity<>(orderservice.getOrder(orderId), HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<OrderProductDto>> getProducts(
            @PathVariable("id") Long orderId) {

        return new ResponseEntity<>(orderservice.getProducts(orderId), HttpStatus.OK);
    }

    @PostMapping("/setStatus")
    public ResponseEntity<Void> setOrderStatus(
            @RequestBody SetStatusDto statusDto) {

        orderservice.setOrderStatus(statusDto.getOrderId(), statusDto.getStatus());
        return ResponseEntity.ok().build();
    }


}
