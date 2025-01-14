package com.example.AuthTG.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "revoked_tokens")
public class RevokedToken extends AbstractEntity{

    private String jti;
    private LocalDateTime revokedAt;
    private LocalDateTime expirestAt;
    private String reason;
    private Long userId;
    private String deviceId;

    @Column(name = "jti")
    public String getJti() {
        return jti;
    }

    @Column(name = "revorked_at")
    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    @Column(name = "expirest_at")
    public LocalDateTime getExpirestAt() {
        return expirestAt;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    @Column(name = "device_id")
    public String getDeviceId() {
        return deviceId;
    }
}
