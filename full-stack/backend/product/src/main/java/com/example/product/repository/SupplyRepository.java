package com.example.product.repository;

import com.example.product.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Optional<Supply> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    List<Supply> findByProductId(Long productId);
}
