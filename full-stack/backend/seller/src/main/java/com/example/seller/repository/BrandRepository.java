package com.example.seller.repository;

import com.example.seller.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findBySellerId(Long sellerId);
}
