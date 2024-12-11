package com.example.auth.service;

import com.example.auth.dto.UserDto;
import com.example.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDto createAccount(UserDto userDto);
    User getByUsername(String username);
    UserDetailsService userDetailsService();
}
