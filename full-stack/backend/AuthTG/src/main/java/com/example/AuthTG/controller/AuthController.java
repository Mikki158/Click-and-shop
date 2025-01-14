package com.example.AuthTG.controller;

import com.example.AuthTG.dto.*;
import com.example.AuthTG.entity.User;
import com.example.AuthTG.service.AuthService;
import com.example.AuthTG.service.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthService authService;
    JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/TG_auth")
    public ResponseEntity<TokensDto> tgAuth(
            @RequestParam("id") String id,
            @RequestParam("first_name") String firstName,
            @RequestParam("username") String username,
            @RequestParam("photo_url") String photoUrl,
            @RequestParam("auth_date") String authDate,
            @RequestParam("hash") String hash) {

        Map<String, String> map = new HashMap<>();

        map.put("id", id);
        map.put("first_name", firstName);
        map.put("username", username);
        map.put("photo_url", photoUrl);
        map.put("auth_date", authDate);
        map.put("hash", hash);

        System.out.println("Логи.");
        System.out.println(map.get("id"));
        System.out.println(map.get("first_name"));
        System.out.println(map.get("username"));
        System.out.println(map.get("photo_url"));
        System.out.println(map.get("auth_date"));
        System.out.println(map.get("hash"));

        TokensDto response = jwtService.auth(map);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/update_tokens")
    public ResponseEntity<TokensDto> updateTokens(@RequestBody @Valid UpdateTokensIn reqest) {

        TokensDto response = jwtService.updateTokens(reqest);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {

        User user = jwtService.validationAccessToken(authHeader);

        System.out.println("Валидация");

        Map<String, Object> response = new HashMap<>();

        response.put("valid", true);
        response.put("userId", user.getId());
        response.put("role", user.getRole());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader("Authorization") String authHeader) {

        User user = jwtService.validationAccessToken(authHeader);

        UserDto response = new UserDto(user.getFirstName(), user.getUsername(), user.getPhotoUrl(), user.getRole());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
