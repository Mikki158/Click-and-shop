package com.example.AuthTG.dto;

import lombok.Data;

@Data
public class ResponseValidToken {
    private boolean valid;
    private Long userId;
    private String role;
}
