package com.example.auth.dto;

import com.example.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthCodeDto {
    private Integer auth_code;
    private String username;
    private Role role;
    private LocalDateTime expires_at;
}
