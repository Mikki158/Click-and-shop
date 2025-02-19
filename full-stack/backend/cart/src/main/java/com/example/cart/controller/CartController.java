package com.example.cart.controller;

import com.example.cart.dto.CartDto;
import com.example.cart.dto.CartProductDto;
import com.example.cart.dto.UserDto;
import com.example.cart.entity.CartProduct;
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

    @GetMapping
    public ResponseEntity<List<CartProductDto>> getCartProducts(
            @RequestHeader("Authorization") String authHeader) {

        UserDto user = cartService.verifyAuthentication(authHeader);

        List<CartProductDto> response = cartService.getProducts(user.getUserId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CartProductDto product) {

        UserDto user = cartService.verifyAuthentication(authHeader);

        String response = cartService.addToCart(user.getUserId(), product);

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
            @RequestHeader("Authorization") String authHeader) {

        UserDto user = cartService.verifyAuthentication(authHeader);

        String response = cartService.decreaseQuantity(user.getUserId(), productId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String authHeader) {

        UserDto user = cartService.verifyAuthentication(authHeader);

        String response = cartService.removeFromCart(user.getUserId(), productId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
