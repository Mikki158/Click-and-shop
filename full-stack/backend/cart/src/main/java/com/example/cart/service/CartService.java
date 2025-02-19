package com.example.cart.service;

import com.example.cart.dto.CartDto;
import com.example.cart.dto.CartProductDto;
import com.example.cart.dto.UserDto;
import com.example.cart.entity.CartProduct;

import java.util.List;

public interface CartService {

    UserDto verifyAuthentication(String authHeader);

    List<CartProductDto> getProducts(Long userId);

    String addToCart(Long userId, CartProductDto product);

    CartDto createCart(Long userId);

    String decreaseQuantity(Long userId, Long productId);

    String removeFromCart(Long userId, Long productId);
}
