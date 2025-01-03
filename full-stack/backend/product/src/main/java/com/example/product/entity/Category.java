package com.example.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "categories")
public class Category extends AbstractEntity{

    private String name;
    private String base;
    private Long imageId;
    private Category parentCategory;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "base")
    public String getBase() {
        return base;
    }

    @Column(name = "image_id")
    public Long getImageId() {
        return imageId;
    }

    @ManyToOne
    @JoinColumn(name = "parent_category_id", nullable = true)
    public Category getParentCategory() {
        return parentCategory;
    }
}
