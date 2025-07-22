package com.example.cart.service.impl;

import com.example.cart.dto.UserDto;
import com.example.cart.service.AuthService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public UserDto verifyAuthentication(String authHeader) {

        String url = "https://click-and-shop.ru/api/auth/userInfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<UserDto>() {}
        );

        UserDto responseBody = response.getBody();

        return responseBody;
    }
}
