package com.example.product.mapper;

import com.example.product.dto.ReviewDto;
import com.example.product.entity.Review;
import com.example.product.repository.ProductRepository;
import com.example.product.repository.ReviewRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReviewMapper extends AbstractMapper<Review, ReviewDto>{

    private final ModelMapper mapper;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewMapper(ModelMapper mapper, ProductRepository productRepository, ReviewRepository reviewRepository) {
        super(Review.class, ReviewDto.class);
        this.mapper = mapper;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void setMapper() {
        mapper.createTypeMap(Review.class, ReviewDto.class)
                .addMappings(m -> m.skip(ReviewDto::setProductId))
                .addMappings(m -> m.skip(ReviewDto::setParentReviewId))
                .setPostConverter(toDtoConvert());
        mapper.createTypeMap(ReviewDto.class, Review.class)
                .addMappings(m -> m.skip(Review::setProduct))
                .addMappings(m -> m.skip(Review::setParentReview))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Review source, ReviewDto destination) {
        destination.setProductId(getProductId(source));
        destination.setParentReviewId(getParentReviewId(source));
    }

    private Long getProductId(Review source) {
        return Objects.isNull(source) || Objects.isNull(source.getId())
                ? null
                : (Objects.isNull(source.getProduct())
                    ? null
                    : source.getProduct().getId());
    }

    private Long getParentReviewId(Review source) {
        return Objects.isNull(source) || Objects.isNull(source.getId())
                ? null
                : (Objects.isNull(source.getParentReview())
                    ? null
                    : source.getParentReview().getId());
    }

    @Override
    public void mapSpecificFields(ReviewDto source, Review destination) {
        destination.setProduct(productRepository.findById(source.getProductId()).orElse(null));
        destination.setParentReview(reviewRepository.findById(source.getParentReviewId()).orElse(null));
    }
}
