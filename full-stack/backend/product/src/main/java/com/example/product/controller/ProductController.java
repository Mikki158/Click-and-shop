package com.example.product.controller;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Image;
import com.example.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    ProductService productService;
    private final String rootDir = "D:\\uploads\\";

    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct() {
        return new ResponseEntity<>(productService.createProduct(), HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("regularPrice") Long regulalPrice,
            @RequestParam("discountedPrice") Long discountedPrice,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId) {

        ProductDto response = productService.updateProduct(id, name, regulalPrice, discountedPrice, description, categoryId);
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
}
