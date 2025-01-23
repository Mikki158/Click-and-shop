package com.example.seller.dto;

import lombok.Data;

@Data
public class BrandDto extends AbstractDto{

    private String brandName;
    private Long sellerId;
}
