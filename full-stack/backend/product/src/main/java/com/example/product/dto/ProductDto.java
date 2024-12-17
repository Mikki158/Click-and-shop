package com.example.product.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends AbstractDto{

    private String name;
    private Long price;
    private String description;
    private Long categoryId;
}
