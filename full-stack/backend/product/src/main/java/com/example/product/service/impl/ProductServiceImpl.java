package com.example.product.service.impl;

import com.example.product.dto.*;
import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.entity.Review;
import com.example.product.exception.ErrorDefinitionException;
import com.example.product.exception.ErrorType;
import com.example.product.mapper.ProductMapper;
import com.example.product.mapper.ReviewMapper;
import com.example.product.repository.CategoryRepository;
import com.example.product.repository.ImageRepository;
import com.example.product.repository.ProductRepository;
import com.example.product.repository.ReviewRepository;
import com.example.product.service.AuthService;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ImageRepository imageRepository;

    CategoryService categoryService;
    ProductMapper productMapper;
    ReviewRepository reviewRepository;
    ReviewMapper reviewMapper;

    AuthService authService;

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

        String url = "http://image-container:8082/api/files/product/" + productId.toString();
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

            String url = "http://image-container:8082/api/files/product/" + element.getId().toString();
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
    public List<BrandDto> getBrands(Long userId) {
        String url = "http://seller-container:8083/api/seller/brandList";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", userId.toString());

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

                String url = "http://image-container:8082/api/files/product/" + product.getId().toString();
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

    @Override
    public ReviewDto createReview(Long userId, CreateReviewDto createReviewDto) {

        Product product = productRepository.getReferenceById(createReviewDto.getProductId());

        Review review = new Review(
                product,
                userId,
                ReviewStatus.CHECKING,
                createReviewDto.getRating(),
                createReviewDto.getReviewText(),
                null,
                null);

        Review saveReview = reviewRepository.save(review);

        return reviewMapper.toDto(saveReview);
    }

    @Override
    public List<ReviewDto> getReviewsFromProduct(Long productId) {

        Product product = productRepository.getReferenceById(productId);

        Optional<List<Review>> existingReviews = reviewRepository.findByProductAndStatus(
                product,
                ReviewStatus.APPROVED);

        List<ReviewDto> response = new ArrayList<>();
        List<String> images = new ArrayList<>();

        if (existingReviews.isPresent()) {

            List<Review> reviews = existingReviews.get();

            for (Review review : reviews) {

                images.clear();

                String url = "http://image-container:8082/api/files/review/" + review.getId().toString();
                String result = restTemplate.getForObject(url, String.class);

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    images = objectMapper.readValue(result, new TypeReference<List<String>>() {});
                } catch (Exception e) {
                    e.printStackTrace();
                }

                review.setImagePaths(images);

                ReviewDto reviewDto = reviewMapper.toDto(review);
                UserDto user = authService.getUserInfo(reviewDto.getUserId());
                reviewDto.setUsername(user.getUsername());
                reviewDto.setPhotoUrl(user.getPhotoUrl());

                //response.add(reviewMapper.toDto(review));
                response.add(reviewDto);
            }
        }

        return response;
    }

    @Override
    public List<ReviewDto> getReviews(Long userId) {

        Optional<List<Review>> existingReviews = reviewRepository.findByUserId(userId);
        List<ReviewDto> response = new ArrayList<>();
        List<String> images = new ArrayList<>();

        if (existingReviews.isPresent()) {
            List<Review> reviews = existingReviews.get();

            for (Review review : reviews) {
                images.clear();

                String url = "http://image-container:8082/api/files/review/" + review.getId().toString();
                String result = restTemplate.getForObject(url, String.class);



                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    images = objectMapper.readValue(result, new TypeReference<List<String>>() {});
                } catch (Exception e) {
                    e.printStackTrace();
                }

                review.setImagePaths(images);

                ReviewDto reviewDto = reviewMapper.toDto(review);
                UserDto user = authService.getUserInfo(reviewDto.getUserId());
                reviewDto.setUsername(user.getUsername());
                reviewDto.setPhotoUrl(user.getPhotoUrl());

                response.add(reviewDto);
            }
        }

        return response;
    }

    @Override
    public List<ReviewDto> getCheckingReview() {

        List<ReviewStatus> statuses = List.of(ReviewStatus.CHECKING, ReviewStatus.EDITED);
        Optional<List<Review>> existingReviews = reviewRepository.findByStatusIn(statuses);
        List<ReviewDto> response = new ArrayList<>();
        List<String> images = new ArrayList<>();

        if (existingReviews.isPresent()) {
            List<Review> reviews = existingReviews.get();

            for (Review review : reviews) {
                images.clear();

                String url = "http://image-container:8082/api/files/review/" + review.getId().toString();
                String result = restTemplate.getForObject(url, String.class);
                
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    images = objectMapper.readValue(result, new TypeReference<List<String>>() {});
                } catch (Exception e) {
                    e.printStackTrace();
                }

                review.setImagePaths(images);

                response.add(reviewMapper.toDto(review));
            }
        }

        return response;
    }

    @Override
    public List<ReviewDto> getApprovedReview() {

        List<ReviewStatus> statuses = List.of(ReviewStatus.APPROVED, ReviewStatus.EDITED);
        Optional<List<Review>> existingReviews = reviewRepository.findByStatusIn(statuses);
        List<ReviewDto> response = new ArrayList<>();
        List<String> images = new ArrayList<>();

        if (existingReviews.isPresent()) {
            List<Review> reviews = existingReviews.get();

            for (Review review : reviews) {
                images.clear();

                String url = "http://image-container:8082/api/files/review/" + review.getId().toString();
                String result = restTemplate.getForObject(url, String.class);

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    images = objectMapper.readValue(result, new TypeReference<List<String>>() {});
                } catch (Exception e) {
                    e.printStackTrace();
                }

                review.setImagePaths(images);

                response.add(reviewMapper.toDto(review));
            }
        }

        return response;
    }

    @Override
    public List<Long> getNotReviewProducts(Long userId) {

        String url = "http://cart-container:8084/api/completeOrder/completeProducts";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", userId.toString());

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Long>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Long>>() {}
        );

        List<Long> products = response.getBody();



        Optional<List<Review>> existingReviews = reviewRepository.findByUserId(userId);

        if (existingReviews.isPresent()) {
            List<Review> reviews = existingReviews.get();
            List<ReviewDto> reviewDtos = new ArrayList<>();

            for (Review review : reviews) {

                ReviewDto temp = reviewMapper.toDto(review);
                reviewDtos.add(temp);
            }

            Set<Long> reviewedProductIds = reviewDtos.stream()
                    .map(ReviewDto::getProductId)
                    .collect(Collectors.toSet());

            return products.stream()
                    .filter(productId -> !reviewedProductIds.contains(productId))
                    .collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.getReferenceById(reviewId);

        String url = "http://image-container:8082/api/files/review/" + review.getId().toString();
        restTemplate.delete(url);

        if (review.getStatus() == ReviewStatus.APPROVED) {
            Product product = productRepository.getReferenceById(review.getProduct().getId());

            float oldRating = product.getRating() * product.getRatingCount();

            float newRating = (oldRating - product.getRating()) / (product.getRatingCount() - 1);

            product.setRating(newRating);
            product.setRatingCount(product.getRatingCount() - 1);

            productRepository.save(product);
        }

        reviewRepository.delete(review);
    }

    @Override
    public ReviewDto getReview(Long reviewId) {

        List<String> images = new ArrayList<>();
        Review review = reviewRepository.getReferenceById(reviewId);

        String url = "http://image-container:8082/api/files/review/" + review.getId().toString();
        String result = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            images = objectMapper.readValue(result, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        review.setImagePaths(images);

        return reviewMapper.toDto(review);
    }

    @Override
    public void setStatusReview(Long reviewId, ReviewStatus status) {

        Review review = reviewRepository.getReferenceById(reviewId);

        review.setStatus(status);

        reviewRepository.save(review);

        switch (status) {
            case CHECKING:
                // Отправка сообщения боту о создании отзыва

            case APPROVED:
                Product product = productRepository.getReferenceById(review.getProduct().getId());

                product.setRating((product.getRating() * product.getRatingCount() + review.getRating()) / (product.getRatingCount() + 1));
                product.setRatingCount(product.getRatingCount() + 1);
                productRepository.save(product);
                // Отправка сообщения боту о одобрении отзыва

            case REJECT:
                // Отправка сообщения боту о отклонении отзыва

            case EDITED:
                // Отправка сообщения боту о отклонении редактирования отзыва
        }
    }
}
