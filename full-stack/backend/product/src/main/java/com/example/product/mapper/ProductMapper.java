package com.example.product.mapper;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.repository.CategoryRepository;
import com.example.product.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductDto>{

    private final ModelMapper mapper;
    private final CategoryRepository repository;

    @Autowired
    public ProductMapper(ModelMapper mapper, CategoryRepository repository) {
        super(Product.class, ProductDto.class);
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Product.class, ProductDto.class)
                .addMappings(m -> m.skip(ProductDto::setCategoryId))
                .setPostConverter(toDtoConvert());
        mapper.createTypeMap(ProductDto.class, Product.class)
                .addMappings(m -> m.skip(Product::setCategory))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Product source, ProductDto destination) {
        destination.setCategoryId(getId(source));
    }

    private Long getId(Product source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getCategory().getId();
    }

    @Override
    public void mapSpecificFields(ProductDto source, Product destination) {
        destination.setCategory(repository.findById(source.getCategoryId()).orElse(null));
    }
}
