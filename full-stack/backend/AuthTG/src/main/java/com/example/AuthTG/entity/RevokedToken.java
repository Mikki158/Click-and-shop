package com.example.AuthTG.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "revoked_tokens")
public class RevokedToken extends AbstractEntity{

    @Column(name = "jti")
    private String jti;
    @Column(name = "revorked_at")
    private LocalDateTime revokedAt;
    @Column(name = "expirest_at")
    private LocalDateTime expirestAt;
    @Column(name = "reason")
    private String reason;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "device_id")
    private String deviceId;

}
