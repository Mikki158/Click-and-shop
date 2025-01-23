package com.example.product.service;

import com.example.product.dto.BrandDto;
import com.example.product.dto.ProductDto;
import com.example.product.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public ProductDto createProduct();

    public ProductDto updateProduct(Long productId, String name, Long regularPrice, Long discountedPrice, String description, Long categoryId, Long brandId);

    public ProductDto getProduct(Long productId);

    public List<ProductDto> getAllProducts();

    public String deleteProduct(Long productId);

    List<BrandDto> getBrands(String authHeader);

    List<ProductDto> getProductFromBrand(List<BrandDto> brands);
}
