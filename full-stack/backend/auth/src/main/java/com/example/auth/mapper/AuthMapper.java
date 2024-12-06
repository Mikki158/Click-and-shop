package com.example.auth.mapper;

import com.example.auth.dto.AuthCodeDto;
import com.example.auth.entity.AuthCode;

public class AuthMapper {
    public static AuthCodeDto mapToAuthCodeDto(AuthCode authCode) {
        return new AuthCodeDto(
                authCode.getAuthCode(),
                authCode.getUsername(),
                authCode.getRole(),
                authCode.getExpiresAt()
        );
    }

    public static AuthCode mapToAuthCode(AuthCodeDto authCodeDto) {
        return new AuthCode(
                authCodeDto.getAuth_code(),
                authCodeDto.getUsername(),
                authCodeDto.getRole(),
                authCodeDto.getExpires_at()
        );
    }
}
