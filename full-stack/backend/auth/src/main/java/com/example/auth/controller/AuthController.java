package com.example.auth.controller;

import com.example.auth.dto.AuthCodeDto;
import com.example.auth.dto.AuthResponseDto;
import com.example.auth.dto.SignInRequest;
import com.example.auth.dto.UserDto;
import com.example.auth.exception.*;
import com.example.auth.service.AuthenticationService;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationService authenticationService;
    private UserService userService;

    @GetMapping("/createAuthCode")
    public ResponseEntity<AuthCodeDto> createAuthCode(@RequestHeader Map<String, String> headers,
                                                      @RequestParam("username") String username) {

        if(!headers.containsKey("api_key") && !headers.get("api_key").equals("sicret_api_key"))
            throw new ErrorDefinitionException("r0002", ErrorType.AUTHORIZATION, Map.of());

        AuthCodeDto saveAuthCode = authenticationService.createAuthCode(username);
        return new ResponseEntity<>(saveAuthCode, HttpStatus.CREATED);
    }

    @PostMapping("/checkauthcode")
    public ResponseEntity<AuthResponseDto> checkAuthCode(@RequestBody @Valid SignInRequest request) {
        AuthResponseDto authResponseDto = authenticationService.checkAuthCode(request);
        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/createAccount")
    public ResponseEntity<UserDto> createAccount(@RequestBody UserDto userDto) {
        UserDto newUser = userService.createAccount(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
