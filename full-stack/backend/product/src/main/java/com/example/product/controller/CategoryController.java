package com.example.product.controller;

import com.example.product.dto.CategoryDto;
import com.example.product.entity.Category;
import com.example.product.repository.CategoryRepository;
import com.example.product.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/caregory")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto request) {
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getFullCategory(@PathVariable("id") Long categoryId){
        return new ResponseEntity<>(categoryService.getFullCategory(categoryRepository.getReferenceById(categoryId)), HttpStatus.OK);
    }
}
