package com.example.seller.dto;

import lombok.Data;

@Data
public class CreateRequestSellerDto {

    private String userId;
    private String username;
    private String firstName;
    private String brand;
    private String comment;
}
