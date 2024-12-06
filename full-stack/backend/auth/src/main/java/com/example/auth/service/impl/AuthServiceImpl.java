package com.example.auth.service.impl;

import com.example.auth.dto.AuthCodeDto;
import com.example.auth.dto.AuthResponseDto;
import com.example.auth.dto.RoleDto;
import com.example.auth.dto.UserDto;
import com.example.auth.entity.AuthCode;
import com.example.auth.entity.Role;
import com.example.auth.entity.User;
import com.example.auth.exception.ErrorDefinitionException;
import com.example.auth.exception.ErrorType;
import com.example.auth.mapper.AuthMapper;
import com.example.auth.mapper.RoleMapper;
import com.example.auth.mapper.UserMapper;
import com.example.auth.repository.AuthRepository;
import com.example.auth.repository.RoleRepository;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthRepository authRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public AuthCodeDto createAuthCode(String username, String role) {
        Integer auth_code = 0;
        do {
            Random rnd = new Random();
            auth_code = rnd.nextInt(900000) + 100000;
        }while (!authRepository.findById(auth_code).isEmpty());

        AuthCode authCode = new AuthCode(auth_code, username, role, LocalDateTime.now().plusMinutes(5));
        authRepository.save(authCode);
        return AuthMapper.mapToAuthCodeDto(authCode);
    }

    @Override
    public AuthResponseDto checkAuthCode(String username, Integer authCodeId) {

        if(authRepository.findById(authCodeId).isEmpty())
            throw new ErrorDefinitionException("r0003", ErrorType.INPUT_REQUEST, Map.of(
                    "Username", username,
                    "authCode", String.valueOf(authCodeId)));

        AuthCode authCode = authRepository.getReferenceById(authCodeId);

        if(authCode.getAuthCode().intValue() != authCodeId.intValue() | !authCode.getUsername().equals(username))
            throw new ErrorDefinitionException("r0003", ErrorType.INPUT_REQUEST, Map.of(
                    "Username", username,
                    "authCode", String.valueOf(authCodeId)));

        if(LocalDateTime.now().isAfter(authCode.getExpiresAt()))
            throw new ErrorDefinitionException("r0005", ErrorType.SERVICE, Map.of());

        authRepository.delete(authCode);
        return new AuthResponseDto("success", "token 1234567890", "Авторизация успешна");
    }

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
    public RoleDto createRole(RoleDto roleDto) {

        if(roleDto.getRoleName().isEmpty())
            throw new ErrorDefinitionException("r0004", ErrorType.INPUT_REQUEST, Map.of());

        Role role = RoleMapper.mapToRole(roleDto);

        if(!roleRepository.findById(role.getRoleName()).isEmpty())
            throw new ErrorDefinitionException("r0009", ErrorType.SYSTEM, Map.of("Роль", role.getRoleName()));

        return RoleMapper.mapToRoleDto(roleRepository.save(role));
    }

    @Override
    public Map<String, String> addUserRole(String username, RoleDto roleDto) {

        if(username.isEmpty())
            throw new ErrorDefinitionException("r0006", ErrorType.INPUT_REQUEST, Map.of());

        if(roleDto.getRoleName().isEmpty())
            throw new ErrorDefinitionException("r0004", ErrorType.INPUT_REQUEST, Map.of());

        if(userRepository.findById(username).isEmpty())
            throw new ErrorDefinitionException("r0007", ErrorType.INPUT_REQUEST, Map.of("Username", username));

        if(roleRepository.findById(roleDto.getRoleName()).isEmpty())
            throw new ErrorDefinitionException("r0010", ErrorType.INPUT_REQUEST, Map.of("Роль", roleDto.getRoleName()));

        User user = userRepository.getReferenceById(username);

        String roleName = roleDto.getRoleName();
        Role role = roleRepository.getReferenceById(roleName);

        for(Role userRole: user.getRoles())
            if(userRole.getRoleName().equals(role.getRoleName()))
                throw new ErrorDefinitionException("r0008", ErrorType.SERVICE, Map.of("Роль", userRole.getRoleName()));

        user.addRole(role);
        userRepository.save(user);
        return Map.of(UserMapper.mapToUserDto(user).getUsername(), RoleMapper.mapToRoleDto(role).getRoleName());

    }
}
