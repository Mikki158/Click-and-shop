package com.example.AuthTG.repository;

import com.example.AuthTG.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRoles_Name(String role);
}
