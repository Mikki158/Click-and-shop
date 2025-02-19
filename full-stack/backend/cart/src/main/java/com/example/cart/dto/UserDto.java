package com.example.cart.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long userId;
    private String username;
    private String firstName;
    private String photoUrl;
    private Set<String> role;
}
