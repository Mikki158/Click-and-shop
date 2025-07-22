package com.example.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReviewDto extends AbstractDto{

    private Long productId;
    private Long userId;
    private String username;
    private String photoUrl;
    private String status;
    private int rating;
    private String reviewText;
    private Long parentReviewId;
    private List<String> imagePaths;
}
