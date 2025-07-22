package com.example.image.reposytory;

import com.example.image.entity.ProductImage;
import com.example.image.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    Optional<List<ReviewImage>> findByReviewId(Long reviewId);

    ReviewImage findByFilePath(String path);
}
