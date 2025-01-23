package com.example.seller.repository;

import com.example.seller.entity.CreateRequestSeller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<CreateRequestSeller, Long> {

    boolean existsByUsername(String username);
}
