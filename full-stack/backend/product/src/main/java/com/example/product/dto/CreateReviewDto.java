package com.example.product.dto;

import lombok.Data;

@Data
public class CreateReviewDto {

    private Long productId;
    private Long userId;
    private ReviewStatus status;
    private int rating;
    private String reviewText;
}
