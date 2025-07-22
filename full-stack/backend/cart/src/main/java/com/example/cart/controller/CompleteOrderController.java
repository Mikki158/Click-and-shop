package com.example.cart.controller;

import com.example.cart.dto.UserDto;
import com.example.cart.dto.order.CompleteOrderDto;
import com.example.cart.dto.order.CompleteOrderProductDto;
import com.example.cart.service.AuthService;
import com.example.cart.service.CompleteOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/completeOrder")
public class CompleteOrderController {

    AuthService authService;
    CompleteOrderService completeOrderService;

    @GetMapping()
    public ResponseEntity<List<CompleteOrderDto>> getCompleteOrders(
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        return new ResponseEntity<>(completeOrderService.getCompleteOrders(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompleteOrderDto> getCompleteOrder(
            @PathVariable("id") Long completeOrderId) {

        return new ResponseEntity<>(completeOrderService.getCompleteOrder(completeOrderId), HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<CompleteOrderProductDto>> getCompleteProducts(
            @PathVariable("id") Long completeOrderId) {

        return new ResponseEntity<>(completeOrderService.getCompleteProduct(completeOrderId), HttpStatus.OK);
    }

    @GetMapping("/completeProducts")
    public ResponseEntity<List<Long>> getAllCompleteProducts(
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        return new ResponseEntity<>(completeOrderService.getAllCompleteProducts(userId), HttpStatus.OK);
    }
}
