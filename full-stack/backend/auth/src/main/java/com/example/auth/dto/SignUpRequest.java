package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignUpRequest {

    private String username;
    private Integer authCode;

}
