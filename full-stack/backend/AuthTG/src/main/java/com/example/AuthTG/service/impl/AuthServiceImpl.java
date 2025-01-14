package com.example.AuthTG.service.impl;

import com.example.AuthTG.dto.TokenType;
import com.example.AuthTG.dto.TokensDto;
import com.example.AuthTG.dto.UpdateTokensIn;
import com.example.AuthTG.entity.User;
import com.example.AuthTG.repository.UserRepository;
import com.example.AuthTG.service.AuthService;
import com.example.AuthTG.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;

    @Override
    public boolean findUserById(Long userId) {

        if (userRepository.findById(userId).isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public User getUserById(Long userId) {

        if (userRepository.findById(userId).isEmpty()) {
            throw new RuntimeException("Такого пользователя нет");
        }

        User user = userRepository.getReferenceById(userId);

        return user;
    }

    @Override
    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Не сохранился");
        }

    }
}
