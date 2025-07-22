package com.example.product.service;

import com.example.product.dto.*;
import com.example.product.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto createProduct();

    ProductDto updateProduct(Long productId, String name, Long regularPrice, Long discountedPrice, String description, Long categoryId, Long brandId);

    ProductDto getProduct(Long productId);

    List<ProductDto> getAllProducts();

    String deleteProduct(Long productId);

    List<BrandDto> getBrands(Long userId);

    List<ProductDto> getProductFromBrand(List<BrandDto> brands);

    ReviewDto createReview(Long userId, CreateReviewDto createReviewDto);

    List<ReviewDto> getReviewsFromProduct(Long productId);

    List<ReviewDto> getReviews(Long userId);

    List<ReviewDto> getCheckingReview();

    List<ReviewDto> getApprovedReview();

    List<Long> getNotReviewProducts(Long userId);

    void deleteReview(Long reviewId);

    ReviewDto getReview(Long reviewId);

    void setStatusReview(Long reviewId, ReviewStatus status);
}
