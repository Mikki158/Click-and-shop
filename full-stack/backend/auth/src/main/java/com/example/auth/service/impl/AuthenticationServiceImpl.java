package com.example.auth.service.impl;

import com.example.auth.dto.AuthCodeDto;
import com.example.auth.dto.AuthResponseDto;
import com.example.auth.dto.SignInRequest;
import com.example.auth.entity.AuthCode;
import com.example.auth.entity.User;
import com.example.auth.exception.ErrorDefinitionException;
import com.example.auth.exception.ErrorType;
import com.example.auth.mapper.AuthMapper;
import com.example.auth.repository.AuthenticationRepository;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.AuthenticationService;
import com.example.auth.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;
    private JwtService jwtService;

    @Override
    public AuthCodeDto createAuthCode(String username) {
        Integer auth_code = 0;
        do {
            Random rnd = new Random();
            auth_code = rnd.nextInt(900000) + 100000;
        }while (!authenticationRepository.findById(auth_code).isEmpty());

        User user = userRepository.getReferenceById(username);

        AuthCode authCode = new AuthCode(auth_code, username, user.getRole(), LocalDateTime.now().plusMinutes(5));
        authenticationRepository.save(authCode);
        return AuthMapper.mapToAuthCodeDto(authCode);
    }

    @Override
    public AuthResponseDto checkAuthCode(SignInRequest request) {

        if(authenticationRepository.findById(request.getAuthCode()).isEmpty())
            throw new ErrorDefinitionException("r0003", ErrorType.INPUT_REQUEST, Map.of(
                    "Username", request.getUsername(),
                    "authCode", String.valueOf(request.getAuthCode())));

        AuthCode authCode = authenticationRepository.getReferenceById(request.getAuthCode());

        if(authCode.getAuthCode().intValue() != request.getAuthCode().intValue() | !authCode.getUsername().equals(request.getUsername()))
            throw new ErrorDefinitionException("r0003", ErrorType.INPUT_REQUEST, Map.of(
                    "Username", request.getUsername(),
                    "authCode", String.valueOf(request.getAuthCode())));

        if(LocalDateTime.now().isAfter(authCode.getExpiresAt()))
            throw new ErrorDefinitionException("r0005", ErrorType.SERVICE, Map.of());

        var user = userRepository.getReferenceById(request.getUsername());

        var jwt = jwtService.generateToken(user);

        authenticationRepository.delete(authCode);
        return new AuthResponseDto("success", jwt, "Авторизация успешна");
    }
}
