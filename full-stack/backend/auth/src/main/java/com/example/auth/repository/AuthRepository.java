package com.example.auth.repository;

import com.example.auth.entity.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthCode, Integer> {
}
