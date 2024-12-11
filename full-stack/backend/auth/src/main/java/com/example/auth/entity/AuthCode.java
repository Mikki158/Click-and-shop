package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_codes")
public class AuthCode {
    @Id
    @Column(name = "auth_code")
    private Integer authCode;
    @Column(name = "username")
    private String username;
    @Column(name = "role")
    private Role role;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
