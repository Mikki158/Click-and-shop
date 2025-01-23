package com.example.AuthTG.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokensDto {

    private String accessToken;
    private String refreshToken;
    private Date nbf;
}
