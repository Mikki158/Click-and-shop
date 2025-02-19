package com.example.cart.mapper;

import com.example.cart.dto.CartDto;
import com.example.cart.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartMapper extends AbstractMapper<Cart, CartDto> {

    public CartMapper() {
        super(Cart.class, CartDto.class);
    }
}
