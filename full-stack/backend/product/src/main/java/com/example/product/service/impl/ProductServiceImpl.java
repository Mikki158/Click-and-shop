package com.example.product.service.impl;

import com.example.product.dto.BrandDto;
import com.example.product.dto.ProductDto;
import com.example.product.entity.Category;
import com.example.product.entity.Image;
import com.example.product.entity.Product;
import com.example.product.exception.ErrorDefinitionException;
import com.example.product.exception.ErrorType;
import com.example.product.mapper.ProductMapper;
import com.example.product.repository.CategoryRepository;
import com.example.product.repository.ImageRepository;
import com.example.product.repository.ProductRepository;
import com.example.product.service.CategoryService;
import com.example.product.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ImageRepository imageRepository;

    CategoryService categoryService;
    ProductMapper productMapper;

    private final String rootDir = "D:\\uploads\\";

    @Override
    public ProductDto createProduct() {
        Product product = new Product();

        Product saveProduct = productRepository.save(product);

        return productMapper.toDto(saveProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long productId, String name, Long regularPrice, Long discountedPrice, String description, Long categoryId, Long brandId) {

        if (productRepository.findById(productId).isEmpty()) {
            throw new RuntimeException("Данного товара нет");
        }
        if (categoryRepository.findById(categoryId).isEmpty())
        {
            throw new RuntimeException("Категория не найдена");
        }

        Product product = productRepository.getReferenceById(productId);

        Category category = categoryRepository.getReferenceById(categoryId);

        product.setName(name);
        product.setRegularPrice(regularPrice);
        product.setDiscountedPrice(discountedPrice);
        product.setDescription(description);
        product.setCategory(category);
        product.setBrandId(brandId);

        Product saveProduct = productRepository.save(product);

        ProductDto productDto = productMapper.toDto(saveProduct);

        return productDto;
    }

    @Override
    public ProductDto getProduct(Long productId) {
        if(productRepository.findById(productId).isEmpty())
            throw new ErrorDefinitionException("r0100", ErrorType.SERVICE, Map.of());

        Product product = productRepository.getReferenceById(productId);
        List<String> images = new ArrayList<>();

        String url = "http://83.147.254.92:8082/api/files/product/" + productId.toString();
        String result = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            images = objectMapper.readValue(result, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        product.setImagePaths(images);

        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = new ArrayList();
        List<ProductDto> response = new ArrayList<>();

        products = productRepository.findAll();

        for (Product element: products) {

            String url = "http://83.147.254.92:8082/api/files/product/" + element.getId().toString();
            String result = restTemplate.getForObject(url, String.class);
            List<String> images = new ArrayList<>();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                images = objectMapper.readValue(result, new TypeReference<List<String>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }

            element.setImagePaths(images);
            response.add(productMapper.toDto(element));
        }

        return response;
    }

    @Override
    public String deleteProduct(Long productId) {

        if (productRepository.findById(productId).isEmpty()) {
            throw new RuntimeException("Такого товара нет");
        }

        productRepository.deleteById(productId);

        return "Товар с id " + productId + " был удален";
    }

    @Override
    public List<BrandDto> getBrands(String authHeader) {
        String url = "https://click-and-shop.ru/api/seller/brandList";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<BrandDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<BrandDto>>() {}
        );

        List<BrandDto> responseBody = response.getBody();

        return responseBody;
    }

    @Override
    public List<ProductDto> getProductFromBrand(List<BrandDto> brands) {

        List<ProductDto> response = new ArrayList<>();

        for (BrandDto brand : brands) {
            List<Product> products = productRepository.findByBrandId(brand.getId());
            for (Product product : products) {

                String url = "http://83.147.254.92:8082/api/files/product/" + product.getId().toString();
                String result = restTemplate.getForObject(url, String.class);
                List<String> images = new ArrayList<>();

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    images = objectMapper.readValue(result, new TypeReference<List<String>>() {});
                } catch (Exception e) {
                    e.printStackTrace();
                }

                product.setImagePaths(images);

                response.add(productMapper.toDto(product));
            }
        }

        return response;
    }
}
