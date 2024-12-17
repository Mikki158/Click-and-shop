package com.example.product.service.impl;

import com.example.product.dto.CreateProductRequest;
import com.example.product.dto.ProductDto;
import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.mapper.ProductMapper;
import com.example.product.repository.CategoryRepository;
import com.example.product.repository.ProductRepository;
import com.example.product.service.CategoryService;
import com.example.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    CategoryService categoryService;
    ProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductDto reqest) {

        if(categoryRepository.findById(reqest.getCategoryId()).isEmpty())
        {
            throw new RuntimeException("Категория не найдена");
        }

        Product product = productMapper.toEntity(reqest);

        Product saveProduct = productRepository.save(product);

        ProductDto productDto = productMapper.toDto(saveProduct);

        return productDto;
    }

    @Override
    public ProductDto getProduct(Long productId) {
        if(productRepository.findById(productId).isEmpty())
            throw new RuntimeException("Такого товара нет");

        return productMapper.toDto(productRepository.getReferenceById(productId));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = new ArrayList();
        List<ProductDto> response = new ArrayList<>();

        products = productRepository.findAll();

        for (Product element: products) {
            response.add(productMapper.toDto(element));
        }

        return response;
    }
}
