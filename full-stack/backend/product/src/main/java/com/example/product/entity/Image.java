package com.example.product.entity;

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

    private Product product;

    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public Image(String filePath) {
        this.filePath = filePath;
    }
}
