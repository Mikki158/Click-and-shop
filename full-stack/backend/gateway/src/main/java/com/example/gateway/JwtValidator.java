package com.example.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtValidator {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isValid(String authorizationHeader) {

        try {
            parseClaims(authorizationHeader);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    public String extractUserId(String token) {
        Claims claims = parseClaims(token);
        return claims.get("sub", String.class);
    }

    public List<String> extractUserRoles(String token) {
        Claims claims = parseClaims(token);

        String rolesString = claims.get("roles", String.class);

        List<String> roles = Arrays.stream(rolesString.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        return roles;
    }

    private Claims parseClaims(String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }

        String token = authorizationHeader.substring(7);

        Claims claims;
        try {
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(getSignKey()).
                    build().parseClaimsJws(token);
            claims = jwsClaims.getPayload();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }

        if (!"ACCESS".equals(claims.get("type"))) {
            throw new RuntimeException("Incorrect token type");
        }

        return claims;
    }
}
