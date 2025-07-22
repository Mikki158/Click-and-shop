package com.example.image.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ImageSercice {

    String addProductImage(MultipartFile image, Long productId);

    String addReviewImage(MultipartFile image, Long reviewId);

    String deleteProductImage(String filePath);

    String deleteReviewImage(String filePath);

    void deleteImageFromReview(Long reviewId);

    List<String> getImageForProduct(Long productId);

    List<String> getImageForReview(Long reviewId);
}
