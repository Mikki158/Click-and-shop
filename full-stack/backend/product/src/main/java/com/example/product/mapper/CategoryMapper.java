package com.example.product.mapper;

import com.example.product.dto.CategoryDto;
import com.example.product.entity.Category;
import com.example.product.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryMapper extends AbstractMapper<Category, CategoryDto>{

    private final ModelMapper mapper;
    private final CategoryRepository repository;

    @Autowired
    public CategoryMapper(ModelMapper mapper, CategoryRepository repository) {
        super(Category.class, CategoryDto.class);
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Category.class, CategoryDto.class)
                .addMappings(m -> m.skip(CategoryDto::setParentCategoryId))
                .setPostConverter(toDtoConvert());
        mapper.createTypeMap(CategoryDto.class, Category.class)
                .addMappings(m -> m.skip(Category::setParentCategory))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Category source, CategoryDto destination) {
        destination.setParentCategoryId(getId(source));
    }

    private Long getId(Category source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getParentCategory().getId();
    }

    @Override
    public void mapSpecificFields(CategoryDto source, Category destination) {
        destination.setParentCategory(repository.findById(source.getParentCategoryId()).orElse(null));
    }
}
