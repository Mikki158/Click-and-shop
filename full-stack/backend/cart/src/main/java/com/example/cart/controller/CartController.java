package com.example.cart.controller;

import com.example.cart.dto.cart.CartDto;
import com.example.cart.dto.cart.CartProductDto;
import com.example.cart.dto.UserDto;
import com.example.cart.service.AuthService;
import com.example.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

    CartService cartService;
    AuthService authService;

    @GetMapping
    public ResponseEntity<List<CartProductDto>> getCartProducts(
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        List<CartProductDto> response = cartService.getProducts(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CartProductDto product) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        String response = cartService.addToCart(userId, product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/createCart")
    public ResponseEntity<CartDto> createCart(
            @RequestBody CartDto cart) {

        CartDto response = cartService.createCart(cart.getUserId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{productId}/decrease")
    public ResponseEntity<String> decreaseQuantity(
            @PathVariable Long productId,
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        String response = cartService.decreaseQuantity(userId, productId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable Long productId,
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        String response = cartService.removeFromCart(userId, productId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> clearCart(
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        cartService.clearCart(userId);

        return ResponseEntity.ok().build();
    }
}
