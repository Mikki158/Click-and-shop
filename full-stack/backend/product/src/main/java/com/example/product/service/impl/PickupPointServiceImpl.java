package com.example.product.service.impl;

import com.example.product.dto.PickupPointDto;
import com.example.product.entity.PickupPoint;
import com.example.product.mapper.PickupPointMapper;
import com.example.product.repository.PickupPointRepository;
import com.example.product.service.PickupPointService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PickupPointServiceImpl implements PickupPointService {

    PickupPointMapper pickupPointMapper;
    PickupPointRepository pickupPointRepository;

    @Override
    public PickupPointDto createPickupPoint(PickupPointDto pickupPointDto) {

        PickupPoint pickupPoint = pickupPointRepository.save(pickupPointMapper.toEntity(pickupPointDto));

        return pickupPointMapper.toDto(pickupPoint);
    }

    @Override
    public List<PickupPointDto> getPickupPoints() {

        List<PickupPoint> pickupPoints = pickupPointRepository.findAll();
        List<PickupPointDto> response = new ArrayList<>();

        for (PickupPoint pickupPoint : pickupPoints) {
            response.add(pickupPointMapper.toDto(pickupPoint));
        }

        return response;
    }

    @Override
    public PickupPointDto getPickupPoint(Long pickupPointId) {

        return pickupPointMapper.toDto(pickupPointRepository.getReferenceById(pickupPointId));
    }
}
