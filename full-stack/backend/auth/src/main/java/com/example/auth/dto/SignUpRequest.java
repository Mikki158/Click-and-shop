package com.example.auth.dto;

import com.example.auth.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Username пользователя", example = "Jon")
    @Size(min = 5, max = 32, message = "Username должен содержать от 5 до 32 символов")
    private String username;

    @Schema(description = "Роль пользователя", example = "User")
    private Role role;
}
