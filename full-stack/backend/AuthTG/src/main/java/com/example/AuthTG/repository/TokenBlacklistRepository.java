package com.example.AuthTG.repository;

import com.example.AuthTG.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<RevokedToken, Long> {

    boolean existsByJti(String jti);
}
