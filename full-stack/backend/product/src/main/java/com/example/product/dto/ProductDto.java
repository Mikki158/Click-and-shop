package com.example.product.dto;

import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends AbstractDto{

    private String name;
    private Long regularPrice;
    private Long discountedPrice;
    private String description;
    private Long categoryId;
    private Long brandId;
	private List<String> imagePaths;
}
