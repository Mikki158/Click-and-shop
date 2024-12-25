package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Username пользователя", example = "Jon")
    private String username;

    @Schema(description = "Код авторизации", example = "123456")
    private Integer authCode;

}
