package com.example.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание товара")
public class CreateProductRequest {

    @Schema(description = "Название товара")
    @Size(min = 5, max = 100, message = "Название товара должно содержать от 5 до 100 символов")
    private String name;

    @Schema(description = "Цена товара")
    private Long price;

    @Schema(description = "Описание товара")
    @Size(max = 500, message = "Описание товара должно содержать до 500 символов")
    private String description;

    @Schema(description = "Id категории товара")
    private Long categoryId;
}
