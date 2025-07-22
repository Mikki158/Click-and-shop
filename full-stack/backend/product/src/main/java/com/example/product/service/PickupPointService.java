package com.example.product.service;

import com.example.product.dto.PickupPointDto;

import java.util.List;

public interface PickupPointService {

    PickupPointDto createPickupPoint(PickupPointDto pickupPoint);

    List<PickupPointDto> getPickupPoints();

    PickupPointDto getPickupPoint(Long pickupPointId);
}
