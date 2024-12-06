package com.example.auth.controller;

import com.example.auth.dto.AuthCodeDto;
import com.example.auth.dto.AuthResponseDto;
import com.example.auth.dto.RoleDto;
import com.example.auth.dto.UserDto;
import com.example.auth.entity.Role;
import com.example.auth.exception.*;
import com.example.auth.mapper.UserMapper;
import com.example.auth.repository.RoleRepository;
import com.example.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private AuthService authService;

    @GetMapping("/createAuthCode")
    public ResponseEntity<AuthCodeDto> createAuthCode(@RequestHeader Map<String, String> headers,
                                                      @RequestParam("username") String username,
                                                      @RequestParam("role") String role) {

        if(!headers.containsKey("api_key") && !headers.get("api_key").equals("sicret_api_key"))
            throw new ErrorDefinitionException("r0002", ErrorType.AUTHORIZATION, Map.of());

        AuthCodeDto saveAuthCode = authService.createAuthCode(username, role);
        return new ResponseEntity<>(saveAuthCode, HttpStatus.CREATED);
    }

    @PostMapping("/checkauthcode")
    public ResponseEntity<AuthResponseDto> checkAuthCode(@RequestParam("username") String username,
                                                         @RequestParam("authCode") Integer authCode) {
        AuthResponseDto authResponseDto = authService.checkAuthCode(username, authCode);
        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/createAccount")
    public ResponseEntity<UserDto> createAccount(@RequestBody UserDto userDto) {
        UserDto newUser = authService.createAccount(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/createRole")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        RoleDto newRole = authService.createRole(roleDto);
        return new ResponseEntity<>(newRole, HttpStatus.OK);
    }

    @PostMapping("/users/{username}/roles")
    public ResponseEntity<Map<String, String>> addRole(@PathVariable(value = "username") String username, @RequestBody RoleDto roleDto) {
        Map<String, String> addingUserRole = authService.addUserRole(username, roleDto);
        return new ResponseEntity<>(addingUserRole, HttpStatus.OK);
    }

}
