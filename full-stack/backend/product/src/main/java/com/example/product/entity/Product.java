package com.example.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "products")
public class Product extends AbstractEntity{

    private String name;
    private Long price;
    private String description;
    private Category category;
    private List<Image> imagePaths;

    public Product(String name, Long price, String description, List<Image> imagePaths) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePaths = imagePaths;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public Category getCategory() {
        return category;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product")
    public List<Image> getImagePaths() {
        return imagePaths;
    }
}