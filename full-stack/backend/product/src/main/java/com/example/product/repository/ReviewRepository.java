package com.example.product.repository;

import com.example.product.dto.ReviewDto;
import com.example.product.dto.ReviewStatus;
import com.example.product.entity.Product;
import com.example.product.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findByProduct(Product product);

    Optional<List<Review>> findByProductAndStatus(Product product, ReviewStatus status);

    Optional<List<Review>> findByStatusIn(List<ReviewStatus> status);

    Optional<List<Review>> findByUserId(Long userId);
}
