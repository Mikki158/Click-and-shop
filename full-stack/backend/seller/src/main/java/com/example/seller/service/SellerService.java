package com.example.seller.service;

import com.example.seller.dto.BrandDto;
import com.example.seller.dto.CreateRequestSellerDto;
import com.example.seller.dto.RequestSellerDto;
import com.example.seller.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface SellerService {

    UserDto verifyAuthentication(Long userId);

    String createRequestSeller(CreateRequestSellerDto request, UserDto user);

    List<RequestSellerDto> getAllRequestSeller();

    String approveRequest (Long requestId);

    String rejectRequest(Long requestId);

    List<BrandDto> getBrandList(Long userId);

    BrandDto getBrand(Long brandId);
}
