package com.example.cart.dto;

import com.example.cart.entity.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartProductDto extends AbstractDto {

    @JsonProperty("id")
    private Long productId;

    @JsonProperty("quantity")
    private int quantity;
    private Long cartId;
}
