package com.example.product.mapper;

import com.example.product.dto.ImageDto;
import com.example.product.entity.Image;
import com.example.product.repository.ImageRepository;
import com.example.product.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ImageMapper extends AbstractMapper<Image, ImageDto> {

    private final ModelMapper mapper;
    private final ProductRepository repository;

    @Autowired
    public ImageMapper(ModelMapper mapper, ProductRepository repository) {
        super(Image.class, ImageDto.class);
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Image.class, ImageDto.class)
                .addMappings(m -> m.skip(ImageDto::setProductId))
                .setPostConverter(toDtoConvert());
        mapper.createTypeMap(ImageDto.class, Image.class)
                .addMappings(m -> m.skip(Image::setProduct))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Image source, ImageDto destination) {
        destination.setProductId(getId(source));
    }

    private Long getId(Image source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getProduct().getId();
    }

    @Override
    public void mapSpecificFields(ImageDto source, Image destination) {
        destination.setProduct(repository.findById(source.getProductId()).orElse(null));
    }
}
