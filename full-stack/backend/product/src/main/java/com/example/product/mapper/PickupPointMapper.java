package com.example.product.mapper;

import com.example.product.dto.PickupPointDto;
import com.example.product.entity.PickupPoint;
import org.springframework.stereotype.Component;

@Component
public class PickupPointMapper extends AbstractMapper<PickupPoint, PickupPointDto>{

    public PickupPointMapper() {
        super(PickupPoint.class, PickupPointDto.class);
    }
}
