package com.example.product.service;

import com.example.product.dto.CategoryDto;
import com.example.product.entity.Category;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto reqest);
    public String getFullCategory(Category category);
}
