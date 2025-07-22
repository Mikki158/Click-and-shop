package com.example.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends AbstractEntity{

    private String name;
    private Long regularPrice;
    private Long discountedPrice;
    private String description;
    private Category category;
    private float rating;
    private int ratingCount;
    private List<String> imagePaths;
    private Long brandId;

    public Product(String name, Long regularPrice, Long discountedPrice, String description, List<String> imagePaths) {
        this.name = name;
        this.regularPrice = regularPrice;
        this.discountedPrice = discountedPrice;
        this.description = description;
        this.imagePaths = imagePaths;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "regular_price")
    public Long getRegularPrice() {
        return regularPrice;
    }

    @Column(name = "discounted_price")
    public Long getDiscountedPrice() {
        return discountedPrice;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    public Category getCategory() {
        return category;
    }

    @Column(name = "brandId")
    public Long getBrandId() {
        return brandId;
    }

    @Column(name = "rating")
    public float getRating() {
        return rating;
    }

    @Column(name = "rating_count")
    public int getRatingCount() {
        return ratingCount;
    }
}