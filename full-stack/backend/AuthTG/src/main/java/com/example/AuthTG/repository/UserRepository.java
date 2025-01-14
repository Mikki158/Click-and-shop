package com.example.AuthTG.repository;

import com.example.AuthTG.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
