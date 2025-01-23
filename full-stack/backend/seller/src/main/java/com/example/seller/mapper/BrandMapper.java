package com.example.seller.mapper;

import com.example.seller.dto.BrandDto;
import com.example.seller.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper extends AbstractMapper<Brand, BrandDto>{

    public BrandMapper() {
        super(Brand.class, BrandDto.class);
    }
}
