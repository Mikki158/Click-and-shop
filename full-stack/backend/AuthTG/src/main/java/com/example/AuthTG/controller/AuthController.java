package com.example.AuthTG.controller;

import com.example.AuthTG.dto.*;
import com.example.AuthTG.entity.Role;
import com.example.AuthTG.entity.User;
import com.example.AuthTG.service.AuthService;
import com.example.AuthTG.service.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

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
            @RequestParam("last_name") String lastName,
            @RequestParam("username") String username,
            @RequestParam("photo_url") String photoUrl,
            @RequestParam("auth_date") String authDate,
            @RequestParam("hash") String hash) {

        Map<String, String> map = new HashMap<>();

        map.put("id", id);
        map.put("first_name", firstName);
        if (!lastName.equals("null"))
            map.put("last_name", lastName);
        if (!photoUrl.equals("null"))
            map.put("photo_url", photoUrl);
        map.put("username", username);
        map.put("auth_date", authDate);
        map.put("hash", hash);

        System.out.println("Логи.");
        System.out.println(map.get("id"));
        System.out.println(map.get("first_name"));
        System.out.println(map.get("last_name"));
        System.out.println(map.get("username"));
        System.out.println(map.get("photo_url"));
        System.out.println(map.get("auth_date"));
        System.out.println(map.get("hash"));

        TokensDto response = jwtService.auth(map);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/TGBot_auth")
    public ResponseEntity<TokensDto> tgBotAuth(
            @RequestBody TGBotAuth tgBotAuth) {

        Map<String, String> map = new HashMap<>();

        map.put("id", tgBotAuth.getUserId());
        map.put("first_name", tgBotAuth.getFirstName());
        if (!tgBotAuth.getLastName().equals("null"))
            map.put("last_name", tgBotAuth.getLastName());
        map.put("username", tgBotAuth.getUsername());

        TokensDto tokens = jwtService.createTokens(map);

        jwtService.saveTGTokens(tokens, tgBotAuth.getUserId());

        return new ResponseEntity<>(tokens, HttpStatus.ACCEPTED);
    }

//    @GetMapping("/getToken/{id}")
//    public ResponseEntity<TokensDto> getToken(
//            @PathVariable Long userId) {
//
//        return new ResponseEntity<>(jwtService.getToken(userId), HttpStatus.OK);
//    }

    @PostMapping("/update_tokens")
    public ResponseEntity<TokensDto> updateTokens(
            @RequestBody @Valid UpdateTokensIn reqest) {

        TokensDto response = jwtService.updateTokens(reqest);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(
            @RequestHeader("X-User-Id") Long userId) {

        //User user = jwtService.validationAccessToken(authHeader);

        User user = authService.getUserById(userId);

        System.out.println("Валидация");

        Map<String, Object> response = new HashMap<>();

        String roles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "));

        response.put("valid", true);
        response.put("userId", user.getId());
        response.put("roles", roles);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserDto> getUserInfo(
            @RequestHeader("X-User-Id") Long userId) {

        //User user = jwtService.validationAccessToken(authHeader);

        User user = authService.getUserById(userId);

        Set<String> roles =  user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        UserDto response = new UserDto(user.getId(), user.getUsername(), user.getFirstName(),
                user.getPhotoUrl(), roles);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addSeller")
    public ResponseEntity<String> addSeller(
            @RequestParam("userId") String userId) {

        String response = authService.addSeller(Long.parseLong(userId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {

        return new ResponseEntity<>(authService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("id") Long userId) {
        
        User user = authService.getUserById(userId);
        UserDto response = new UserDto();
        response.setUsername(user.getUsername());
        response.setPhotoUrl(user.getPhotoUrl());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Long>> getAdmins() {

        return new ResponseEntity<>(authService.getAdmins(), HttpStatus.OK);
    }

}
