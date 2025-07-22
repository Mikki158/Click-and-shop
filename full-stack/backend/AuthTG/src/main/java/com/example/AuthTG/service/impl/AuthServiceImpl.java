package com.example.AuthTG.service.impl;

import com.example.AuthTG.dto.*;
import com.example.AuthTG.entity.Role;
import com.example.AuthTG.entity.User;
import com.example.AuthTG.entity.UserTGTokens;
import com.example.AuthTG.repository.RoleRepository;
import com.example.AuthTG.repository.UserRepository;
import com.example.AuthTG.repository.UserTokensRepository;
import com.example.AuthTG.service.AuthService;
import com.example.AuthTG.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    RoleRepository roleRepository;

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

    @Override
    public String addSeller(Long userId) {

        if (userRepository.findById(userId).isEmpty())
            throw new RuntimeException("Такого пользователя нет");

        User user = userRepository.getReferenceById(userId);

        Set<Role> roles = user.getRoles();

        boolean exists = roles.stream()
                .anyMatch(obj -> obj.getName().equals("Seller"));

        if (!exists)
        {
            Role role = roleRepository.findByName("Seller");

            user.getRoles().add(role);

            saveUser(user);

            return "Пользователь стал продавцом";
        }

        return "Пользователь уже был продавцом";
    }

    @Override
    public User auth(Map<String, String> data) {

        User user;

        if(!findUserById(Long.parseLong(data.get("id")))) {

            System.out.println("Создание пользователя");

            user = new User(Long.parseLong(data.get("id")), data.get("first_name"), data.get("last_name"),
                    data.get("username"), data.get("photo_url"));

            Role role = roleRepository.findByName("User");

            user.getRoles().add(role);

            if (data.get("photo_url") != null)
                user.setPhotoUrl(data.get("photo_url"));

            saveUser(user);

            System.out.println("Пользователь сохранился");
        }
        else {
            user = getUserById(Long.parseLong(data.get("id")));
        }

        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = new ArrayList<>();
        List<UserDto> response = new ArrayList<>();

        users = userRepository.findAll();

        for(User user : users) {

            Set<String> roles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getPhotoUrl(), roles);
            response.add(userDto);
        }

        return response;
    }

    @Override
    public List<Long> getAdmins() {

        List<User> admins = userRepository.findByRoles_Name("Admin");
        List <Long> response = new ArrayList<>();

        for (User user : admins) {

            response.add(user.getId());
        }

        return response;
    }


}
