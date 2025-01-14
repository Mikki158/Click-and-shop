package com.example.AuthTG.service;

import com.example.AuthTG.dto.TokensDto;
import com.example.AuthTG.dto.UpdateTokensIn;
import com.example.AuthTG.entity.User;

import java.util.Map;

public interface JwtService {

    String generateAccessToken(User subject, Map<String, Object> payload);

    String generateRefreshToken(User subject, Map<String, Object> payload);

    Map<String, Object> decodeToken(String token);

    Map<String, Object> verifyToken(String token);

    User validationAccessToken(String authorizationHeader);

    TokensDto auth(Map<String, String> map);

    Map<String, String> checkTelegramAuthorization(Map<String, String> map) throws Exception;

    TokensDto updateTokens(UpdateTokensIn reqest);
}
