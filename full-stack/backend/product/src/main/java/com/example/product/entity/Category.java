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
    private Category parentCategory;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    public Category getParentCategory() {
        return parentCategory;
    }
}
