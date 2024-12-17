package com.example.product.service;

import com.example.product.dto.CreateProductRequest;
import com.example.product.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {

    public ProductDto createProduct(ProductDto request);

    public ProductDto getProduct(Long productId);

    public List<ProductDto> getAllProducts();
}
