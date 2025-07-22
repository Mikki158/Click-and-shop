package com.example.product.controller;

import com.example.product.dto.*;
import com.example.product.service.AuthService;
import com.example.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
@Tag(name = "Products", description = "Управление товарами")
public class ProductController {

    ProductService productService;
    AuthService authService;
    private final String rootDir = "D:\\uploads\\";

    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct() {
        return new ResponseEntity<>(productService.createProduct(), HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto request) {

        ProductDto response = productService.updateProduct(
                request.getId(),
                request.getName(),
                request.getRegularPrice(),
                request.getDiscountedPrice(),
                request.getDescription(),
                request.getCategoryId(),
                request.getBrandId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable("id") Long productId) {

        ProductDto response = productService.getProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Получить список всех товаров")
    public ResponseEntity<List<ProductDto>> getAllProduct() {

        List<ProductDto> response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {

        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/seller")
    public ResponseEntity<List<ProductDto>> getProductsFromSeller(
            @RequestHeader("X-User-Id") Long userId) {

        List<BrandDto> brands = productService.getBrands(userId);

        List<ProductDto> response = productService.getProductFromBrand(brands);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/createReview")
    public ResponseEntity<ReviewDto> createReview(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateReviewDto reviewDto) {

        //UserDto userDto = authService.verifyAuthentication(authHeader);

        ReviewDto response = productService.createReview(userId, reviewDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/review")
    public ResponseEntity<List<ReviewDto>> getReviewsFromProduct(
            @PathVariable("id") Long productId) {

        return new ResponseEntity<>(productService.getReviewsFromProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewDto>> getReviews(
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto userDto = authService.verifyAuthentication(authHeader);

        return new ResponseEntity<>(productService.getReviews(userId), HttpStatus.OK);
    }

    @GetMapping("/checkingReview")
    public ResponseEntity<List<ReviewDto>> getCheckingReview() {

        return new ResponseEntity<>(productService.getCheckingReview(), HttpStatus.OK);
    }

    @GetMapping("/approvedReview")
    public ResponseEntity<List<ReviewDto>> getApprovedReview() {

        return new ResponseEntity<>(productService.getApprovedReview(), HttpStatus.OK);
    }

    @GetMapping("/notReviewProducts")
    public ResponseEntity<List<Long>> getNotReviewProducts(
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto userDto = authService.verifyAuthentication(authHeader);

        return new ResponseEntity<>(
                productService.getNotReviewProducts(userId),
                HttpStatus.OK);
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("id") Long reviewId) {

        productService.deleteReview(reviewId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<ReviewDto> getReview(
            @PathVariable("id") Long reviewId) {

        return new ResponseEntity<>(productService.getReview(reviewId), HttpStatus.OK);
    }

    @PostMapping("/review")
    public ResponseEntity<Void> setStatusReview(
            @RequestBody SetStatusReviewDto setStatusReviewDto) {

        productService.setStatusReview(setStatusReviewDto.getReviewId(), setStatusReviewDto.getStatus());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviewStatus")
    public ResponseEntity<ReviewStatus[]> getAllStatuses() {

        return new ResponseEntity<>(ReviewStatus.values(), HttpStatus.OK);
    }
}
