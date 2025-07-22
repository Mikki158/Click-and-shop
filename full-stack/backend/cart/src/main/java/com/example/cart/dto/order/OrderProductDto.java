package com.example.cart.dto.order;

import com.example.cart.dto.AbstractDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderProductDto extends AbstractDto {

    @JsonProperty("id")
    private Long productId;
    private int quantity;
    @JsonProperty("discountedPrice")
    private Long price;
    private Long orderId;
}
