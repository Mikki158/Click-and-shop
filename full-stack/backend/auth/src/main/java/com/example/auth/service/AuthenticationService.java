package com.example.auth.service;

import com.example.auth.dto.AuthCodeDto;
import com.example.auth.dto.AuthResponseDto;
import com.example.auth.dto.SignUpRequest;

public interface AuthenticationService {
    AuthCodeDto createAuthCode(String username);
    AuthResponseDto checkAuthCode(SignUpRequest request);
}
