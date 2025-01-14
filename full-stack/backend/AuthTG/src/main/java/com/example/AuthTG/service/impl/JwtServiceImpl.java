package com.example.AuthTG.service.impl;

import com.example.AuthTG.dto.TokenType;
import com.example.AuthTG.dto.TokensDto;
import com.example.AuthTG.dto.UpdateTokensIn;
import com.example.AuthTG.entity.User;
import com.example.AuthTG.repository.TokenBlacklistRepository;
import com.example.AuthTG.service.AuthService;
import com.example.AuthTG.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

    //@Value("${token.access.ttl}")
    private final int accessTokenTtl = 180000;
    //@Value("${token.refresh.ttl}")
    private final int refreshTokenTtl = 259200000;

    //@Value("${token.signig.key}")
    private final String jwtSigningKey = "53A73E5F1C4E0A2D3B5F2D784E6A1B423D6F247D1F6E5C3A596D635A75327855";

    TokenBlacklistRepository blacklistRepository;
    AuthService authService;

    @Override
    public String generateAccessToken(User subject, Map<String, Object> payload) {
        return generateToken(
                "ACCESS",
                subject,
                payload,
                accessTokenTtl
        );
    }

    @Override
    public String generateRefreshToken(User subject, Map<String, Object> payload) {
        return generateToken(
                "REFRESH",
                subject,
                payload,
                refreshTokenTtl
        );
    }


    private String generateToken(String type, User subject, Map<String, Object> payload, int ttl) {

        Map<String, Object> claims = new HashMap<>();

        Date currentTimestamp = new Date(System.currentTimeMillis());
        Date nextTimestamp = new Date(System.currentTimeMillis() + ttl);

        claims.put("iss", "Mikki@auth_service");
        claims.put("sub", subject.getId().toString());
        claims.put("type", type);
        claims.put("jti", UUID.randomUUID().toString());
        claims.put("iat", currentTimestamp);
        claims.put("nbf", currentTimestamp);
        claims.put("exp", nextTimestamp);

        String token = Jwts.builder().subject(subject.getId().toString()).claims(claims).signWith(getSignKey())
                .issuedAt(currentTimestamp).expiration(nextTimestamp)
                .compact();

        //claims.put("role", user.getRole());

//        Jwts.builder().claims(claims).subject(user.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
//                .signWith(getSignKey()).compact();

        return token;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Map<String, Object> decodeToken(String token) {
        Map<String, Object> payload = verifyToken(token);

        return payload;
    }

    @Override
    public Map<String, Object> verifyToken(String token) {
//        Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(getSignKey()).
//                build().parseClaimsJws(token);

        Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token);

        Map<String, Object> payload = new HashMap<>();

        Claims claims = jwsClaims.getPayload();
        payload.put("iss", claims.getIssuer());
        payload.put("sub", claims.getSubject());
        payload.put("type", claims.get("type", String.class));
        payload.put("jti", claims.getId());
        payload.put("iat", claims.get("iat", Date.class));
        payload.put("nbf", claims.get("nbf", Date.class));
        payload.put("exp", claims.get("exp", Date.class));

        return payload;
    }

    @Override
    public User validationAccessToken(String authorizationHeader) {

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
            throw new RuntimeException("Invalid or expired token");
        }

        if (!"ACCESS".equals(claims.get("type"))) {
            throw new RuntimeException("Incorrect token type");
        }

        if (isTokenRevoked(claims.getId())) {
            throw new RuntimeException("Token is revoked");
        }

        Long userId = Long.parseLong(claims.get("sub", String.class));
        User user = authService.getUserById(userId);

        return user;
    }

    private boolean isTokenRevoked(String jti) {
        if (blacklistRepository.existsByJti(jti))
            return true;

        return false;
    }

    @Override
    public TokensDto auth(Map<String, String> map) {
        try {
            Map<String, String> data = checkTelegramAuthorization(map);

            User user;

            System.out.println("Логи3.");
            System.out.println(map.get("id"));
            System.out.println(Long.parseLong(data.get("id")));

            if(!authService.findUserById(Long.parseLong(data.get("id")))) {
                user = new User(Long.parseLong(data.get("id")), data.get("first_name"), data.get("username"),
                        data.get("photo_url"), "Admin");

                authService.saveUser(user);
            }
            else {
                user = authService.getUserById(Long.parseLong(data.get("id")));
            }

            return issueTokensForUser(user);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private TokensDto issueTokensForUser(User user) {

        String accessToken = generateAccessToken(user, null);
        String refreshToken = generateRefreshToken(user, null);

        return new TokensDto(accessToken, refreshToken);
    }

    @Override
    public Map<String, String> checkTelegramAuthorization(Map<String, String> map) throws Exception {
        String botToken = "7879270677:AAF_BdUvUylE9XXY8MpTOk8lpiWZL3cMy24";
        Long currentTime = System.currentTimeMillis() / 1000;

        String checkHash = map.get("hash");
        map.remove("hash");

        System.out.println("Логи2.");
        System.out.println(map.get("id"));
        System.out.println(map.get("first_name"));
        System.out.println(map.get("username"));
        System.out.println(map.get("photo_url"));
        System.out.println(map.get("auth_date"));

        List<String> dataCheckArr = new ArrayList<>();

        for(Map.Entry<String, String> entity : map.entrySet()) {
            dataCheckArr.add(entity.getKey() + '=' + entity.getValue());
        }

        Collections.sort(dataCheckArr);
        String dataCheckString = String.join("\n", dataCheckArr);

        byte[] secretKey = sha256(botToken);

        String hash = hmacSha256(dataCheckString, secretKey);

        if (!hash.equals(checkHash)) {
            throw new Exception("Data is NOT from Telegram");
        }

        if (currentTime - Long.parseLong((map.get("auth_date"))) > 86400) {
            throw new Exception("Data is outdated");
        }

        return map;

    }

    // Метод для вычисления SHA-256
    private byte[] sha256(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    // Метод для вычисления HMAC-SHA256
    private String hmacSha256(String data, byte[] key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hmacBytes);
    }

    // Метод для преобразования байтов в шестнадцатеричную строку
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public TokensDto updateTokens(UpdateTokensIn reqest) {
        Map<String, Object> payload = decodeToken(reqest.getRefreshToken());

        System.out.println(payload.get("type").getClass());

        if (!payload.get("type").equals("REFRESH")) {
            throw new RuntimeException("Не то");
        }

        Long userId = Long.parseLong(payload.get("sub").toString());

//        if (userRepository.findById(userId).isEmpty()) {
//            throw new RuntimeException("Нет такого");
//        }

        //User user = userRepository.getReferenceById(userId);

        User user = authService.getUserById(userId);

        TokensDto tokens = issueTokensForUser(user);

        return tokens;
    }
}
