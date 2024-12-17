package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Username пользователя", example = "Jon")
    @Size(min = 5, max = 32, message = "Username должен содержать от 5 до 32 символов")
    private String username;

    @Schema(description = "Код авторизации", example = "123456")
    @Size(min = 6, max = 6, message = "Код авторизации должен содержать 6 цифр")
    private Integer authCode;

}
