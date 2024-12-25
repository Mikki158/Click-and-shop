package com.example.product.controller;

import com.example.product.dto.ProductDto;
import com.example.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto request) {

        ProductDto response = productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {

        ProductDto response = productService.getProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProduct() {

        List<ProductDto> response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/addPhoto")
    public ResponseEntity<String> addPhoto(@RequestParam("file") MultipartFile image) {

        String response = productService.addImage(image);
        return ResponseEntity.ok(response);
    }
}
