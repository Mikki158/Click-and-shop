package com.example.cart.service;

import com.example.cart.dto.cart.CartDto;
import com.example.cart.dto.cart.CartProductDto;

import java.util.List;

public interface CartService {

    List<CartProductDto> getProducts(Long userId);

    String addToCart(Long userId, CartProductDto product);

    CartDto createCart(Long userId);

    String decreaseQuantity(Long userId, Long productId);

    String removeFromCart(Long userId, Long productId);

    void clearCart(Long userId);
}
