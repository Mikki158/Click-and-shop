package com.example.image.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "images")
public class Image extends AbstractEntity{

    private String filePath;

    private Long productId;

    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    @Column(name = "product_id", nullable = false)
    public Long getProductId() {
        return productId;
    }
}
