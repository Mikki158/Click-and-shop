package com.example.product.dto;

import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends AbstractDto{

    private String name;
    private Long price;
    private String description;
    private Long categoryId;
    private List<ImageDto> imagePaths;
}
