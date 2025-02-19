package com.example.seller.service;

import com.example.seller.dto.BrandDto;
import com.example.seller.dto.CreateRequestSellerDto;
import com.example.seller.dto.RequestSellerDto;
import com.example.seller.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface SellerService {

    UserDto verifyAuthentication(String authHeader);

    String createRequestSeller(CreateRequestSellerDto request, UserDto user);

    List<RequestSellerDto> getAllRequestSeller();

    String approveRequest (Long requestId);

<<<<<<< Updated upstream
    List<BrandDto> getBrandList(UserDto user);
=======
    String rejectRequest(Long requestId);

    List<BrandDto> getBrandList(UserDto user);

    BrandDto getBrand(Long brandId);
>>>>>>> Stashed changes
}
