package com.example.AuthTG.repository;

import com.example.AuthTG.entity.UserTGTokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokensRepository extends JpaRepository<UserTGTokens, Long> {

    Optional<UserTGTokens> findByUserId(Long userId);
}
