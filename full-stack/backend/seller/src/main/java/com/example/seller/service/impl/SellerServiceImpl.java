package com.example.seller.service.impl;
import com.example.seller.dto.BrandDto;
import com.example.seller.dto.CreateRequestSellerDto;
import com.example.seller.dto.RequestSellerDto;
import com.example.seller.dto.UserDto;
import com.example.seller.entity.Brand;
import com.example.seller.entity.CreateRequestSeller;
import com.example.seller.mapper.BrandMapper;
import com.example.seller.mapper.RequestMapper;
import com.example.seller.repository.BrandRepository;
import com.example.seller.repository.RequestRepository;
import com.example.seller.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SellerServiceImpl implements SellerService {

    private RequestRepository requestRepository;
    private BrandRepository brandRepository;
    private RequestMapper requestMapper;
    private BrandMapper brandMapper;

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

    @Override
    public String createRequestSeller(CreateRequestSellerDto request, UserDto user) {

        CreateRequestSeller newRequest = new CreateRequestSeller();

        newRequest.setUserId(user.getUserId());
        newRequest.setUsername(user.getUsername());
        newRequest.setFirstName(user.getFirstName());
        newRequest.setBrand(request.getBrand());
        newRequest.setComment(request.getComment());

        if (requestRepository.existsByUsername(newRequest.getUsername()))
            throw new RuntimeException("Заявка уже есть");

        CreateRequestSeller saveReuest = requestRepository.save(newRequest);

        return "Заявка №" + saveReuest.getId() + " отправлена, ожидайте проверки";
    }

    @Override
    public List<RequestSellerDto> getAllRequestSeller() {

        List<CreateRequestSeller> requestSellers = requestRepository.findAll();
        List<RequestSellerDto> response = new ArrayList<>();

        for (CreateRequestSeller request : requestSellers) {

            RequestSellerDto requestSellerDto = requestMapper.toDto(request);

            response.add(requestSellerDto);
        }

        return response;
    }

    @Override
    public String approveRequest (Long requestId) {

        if (requestRepository.findById(requestId).isEmpty()) {
            throw new RuntimeException("Такой заявки нету");
        }

        CreateRequestSeller request = requestRepository.getReferenceById(requestId);

        Brand brand = new Brand(request.getBrand(), request.getUserId());

        brandRepository.save(brand);

        requestRepository.deleteById(requestId);

        String url = "https://click-and-shop.ru/api/auth/addSeller?userId=" + request.getUserId().toString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<String>() {}
        );

        String result = response.getBody();

        return "Заявка была одобрена, " + result;
    }

    @Override
    public List<BrandDto> getBrandList(UserDto user) {

        List<Brand> brands = brandRepository.findBySellerId(user.getUserId());
        List<BrandDto> response = new ArrayList<>();

        for (Brand brand : brands) {
            BrandDto brandDto = brandMapper.toDto(brand);

            response.add(brandDto);
        }

        return response;
    }
}
