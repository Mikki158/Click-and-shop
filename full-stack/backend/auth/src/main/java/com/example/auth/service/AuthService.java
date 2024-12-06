package com.example.auth.service;

import com.example.auth.dto.AuthCodeDto;
import com.example.auth.dto.AuthResponseDto;
import com.example.auth.dto.RoleDto;
import com.example.auth.dto.UserDto;
import com.example.auth.entity.Role;
import com.example.auth.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface AuthService {
    AuthCodeDto createAuthCode(String username, String role);

    AuthResponseDto checkAuthCode(String username, Integer authCode);

    UserDto createAccount(UserDto userDto);

    RoleDto createRole(RoleDto roleDto);

    Map<String, String> addUserRole(String username, RoleDto roleDto);
}
