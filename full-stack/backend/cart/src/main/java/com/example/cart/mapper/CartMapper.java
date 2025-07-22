package com.example.cart.mapper;

import com.example.cart.dto.cart.CartDto;
import com.example.cart.entity.cart.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartMapper extends AbstractMapper<Cart, CartDto> {

    public CartMapper() {
        super(Cart.class, CartDto.class);
    }
}
