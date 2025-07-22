package com.example.product.service.impl;

import com.example.product.dto.BrandDto;
import com.example.product.dto.UserDto;
import com.example.product.service.AuthService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public UserDto verifyAuthentication(String authHeader) {

        String url = "http://auth-container:8080/api/auth/userInfo";

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

    @Override
    public List<BrandDto> getBrands(String authHeader) {
        String url = "http://auth-container:8080/api/seller/brandList";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<BrandDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<BrandDto>>() {}
        );

        List<BrandDto> responseBody = response.getBody();

        return responseBody;
    }

    @Override
    public UserDto getUserInfo(Long userId) {

        String url = "http://auth-container:8080/api/auth/" + userId.toString();

        HttpHeaders headers = new HttpHeaders();

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
