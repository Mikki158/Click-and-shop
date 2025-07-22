package com.example.image.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "review_images")
public class ReviewImage extends AbstractEntity{

    private String filePath;

    private Long reviewId;

    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    @Column(name = "review_id", nullable = false)
    public Long getReviewId() {
        return reviewId;
    }
}
