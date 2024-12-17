package com.example.product.entity;

import jakarta.persistence.*;
import lombok.*;

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

    public Product(String name, Long price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
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
}