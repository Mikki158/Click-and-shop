package com.example.auth.service.impl;

import com.example.auth.dto.UserDto;
import com.example.auth.entity.User;
import com.example.auth.exception.ErrorDefinitionException;
import com.example.auth.exception.ErrorType;
import com.example.auth.mapper.UserMapper;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto createAccount(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        if(userRepository.existsById(user.getUsername()))
            throw new ErrorDefinitionException(
                    "r0001",
                    ErrorType.SERVICE,
                    Map.of("Username:", user.getUsername()));

        User saveUser = userRepository.save(user);
        return UserMapper.mapToUserDto(saveUser);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
