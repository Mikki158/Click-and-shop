package com.example.cart.dto.favorite;

import com.example.cart.dto.AbstractDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FavoriteProductDto extends AbstractDto {

    @JsonProperty("id")
    private Long productId;

    private Long favoriteId;
}
