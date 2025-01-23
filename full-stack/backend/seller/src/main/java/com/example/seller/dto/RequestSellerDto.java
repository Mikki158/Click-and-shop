package com.example.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSellerDto extends AbstractDto{

    private Long userId;
    private String username;
    private String firstName;
    private String brand;
    private String comment;
}
