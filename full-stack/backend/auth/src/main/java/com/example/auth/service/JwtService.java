package com.example.auth.service;

import com.example.auth.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    public String extractUserName(String token);
    public String generateToken(User user);
    public boolean isTokenValid(String token, UserDetails user);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
