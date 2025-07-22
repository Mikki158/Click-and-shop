package com.example.cart.dto.order;

import com.example.cart.dto.AbstractDto;
import lombok.Data;

@Data
public class SetStatusDto extends AbstractDto {

    private Long orderId;
    private String status;
}
