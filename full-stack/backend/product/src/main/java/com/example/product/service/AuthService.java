package com.example.product.service;


import com.example.product.dto.BrandDto;
import com.example.product.dto.UserDto;
import org.apache.catalina.User;

import java.util.List;

public interface AuthService {

    UserDto verifyAuthentication(String authHeader);

    List<BrandDto> getBrands(String authHeader);

    UserDto getUserInfo(Long userId);
}
