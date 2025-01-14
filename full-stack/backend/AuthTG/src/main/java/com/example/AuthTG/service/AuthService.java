package com.example.AuthTG.service;

import com.example.AuthTG.dto.TokensDto;
import com.example.AuthTG.dto.UpdateTokensIn;
import com.example.AuthTG.entity.User;

import java.util.Map;

public interface AuthService {

    boolean findUserById(Long userId);

    User getUserById(Long userId);

    void saveUser(User user);
}
