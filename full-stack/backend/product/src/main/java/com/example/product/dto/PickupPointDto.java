package com.example.product.dto;

import lombok.Data;

@Data
public class PickupPointDto extends AbstractDto{

    private String address;
    private String phoneNumber;
}
