package com.example.cart.service;

import com.example.cart.dto.UserDto;

public interface AuthService {

    UserDto verifyAuthentication(String authHeader);
}
