package com.example.product.service.impl;

import com.example.product.dto.CategoryDto;
import com.example.product.entity.Category;
import com.example.product.mapper.CategoryMapper;
import com.example.product.repository.CategoryRepository;
import com.example.product.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryDto request) {
        Category category = categoryMapper.toEntity(request);
        Category saveCategory = categoryRepository.save(category);
        CategoryDto response = categoryMapper.toDto(saveCategory);
        return response;
    }

    @Override
    public String getFullCategory(Category category) {
        if(category.getParentCategory() == null)
            return category.getName() + "/";

        return getFullCategory(category.getParentCategory()) + category.getName() + "/";
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategories = categoryRepository.findAll();
        List<CategoryDto> response = new ArrayList<>();

        for (Category category : allCategories) {
            CategoryDto temp = categoryMapper.toDto(category);
            response.add(temp);
        }

        return response;
    }
}
