package com.example.AuthTG.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_tokens")
public class UserTGTokens extends AbstractEntity{

    private Long userId;
    private String accessToken;
    private String refreshToken;

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @Column(name = "refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }
}
