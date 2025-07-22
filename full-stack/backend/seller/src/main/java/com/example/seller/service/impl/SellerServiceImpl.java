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
import org.springframework.http.*;
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
    public UserDto verifyAuthentication(Long userId) {

        String url = "http://auth-container:8080/api/auth/userInfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", userId.toString());

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

//        String url = "https://click-and-shop.ru/bot/sellerRequest";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<RequestSellerDto> botRequest = new HttpEntity<>(requestMapper.toDto(saveReuest), headers);
//
//        //HttpEntity<String> entity = new HttpEntity<>("", headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                botRequest,
//                new ParameterizedTypeReference<>() {}
//        );
        
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

        String url = "http://auth-container:8080/api/auth/addSeller?userId=" + request.getUserId().toString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<String>() {}
        );

        String result = response.getBody();

//        url = "https://click-and-shop.ru/bot/approveRequest?requestId=" + request.getId();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<RequestSellerDto> botRequest = new HttpEntity<>(requestMapper.toDto(request), headers);
//
//        //HttpEntity<String> entity = new HttpEntity<>("", headers);
//
//        restTemplate = new RestTemplate();
//        restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                botRequest,
//                new ParameterizedTypeReference<UserDto>() {}
//        );

        return "Заявка была одобрена, " + result;
    }

    @Override
    public String rejectRequest(Long requestId) {

        if (requestRepository.findById(requestId).isEmpty()) {
            throw new RuntimeException("Такой заявки нету");
        }

        CreateRequestSeller request = requestRepository.getReferenceById(requestId);

        requestRepository.deleteById(requestId);


//        String url = "https://click-and-shop.ru/bot/rejectRequest?requestId=" + request.getId();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<RequestSellerDto> botRequest = new HttpEntity<>(requestMapper.toDto(request), headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.exchange(
//                url,
//                HttpMethod.DELETE,
//                botRequest,
//                new ParameterizedTypeReference<CreateRequestSellerDto>() {}
//        );


        return "Заявка №" + requestId + " была удалена";
    }

    @Override
    public List<BrandDto> getBrandList(Long userId) {

        List<Brand> brands = brandRepository.findBySellerId(userId);
        List<BrandDto> response = new ArrayList<>();

        for (Brand brand : brands) {
            BrandDto brandDto = brandMapper.toDto(brand);

            response.add(brandDto);
        }

        return response;
    }

    @Override
    public BrandDto getBrand(Long brandId) {

//        if (brandRepository.findBySellerId(brandId).isEmpty())
//            throw new RuntimeException("Брэнда не существует");

        Brand brand = brandRepository.getReferenceById(brandId);

        BrandDto response = brandMapper.toDto(brand);

        return response;
    }
}
