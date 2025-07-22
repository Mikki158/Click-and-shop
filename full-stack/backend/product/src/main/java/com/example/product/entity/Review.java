package com.example.product.entity;

import com.example.product.dto.ReviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_reviews")
public class Review extends AbstractEntity{

    private Product product;
    private Long userId;
    private ReviewStatus status;
    private int rating;
    private String reviewText;
    private Review parentReview;
    private List<String> imagePaths;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return product;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    @Column(name = "status")
    public ReviewStatus getStatus() {
        return status;
    }

    @Column(name = "rating")
    public int getRating() {
        return rating;
    }

    @Column(name = "review_text")
    public String getReviewText() {
        return reviewText;
    }

    @ManyToOne
    @JoinColumn(name = "parent_review_id", nullable = true)
    public Review getParentReview() {
        return parentReview;
    }
}
