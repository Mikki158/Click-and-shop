package com.example.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private String role;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}