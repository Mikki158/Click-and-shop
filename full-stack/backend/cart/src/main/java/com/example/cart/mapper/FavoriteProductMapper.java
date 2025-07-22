package com.example.cart.mapper;

import com.example.cart.dto.favorite.FavoriteProductDto;
import com.example.cart.entity.favorite.FavoriteProduct;
import com.example.cart.repository.favorite.FavoriteRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FavoriteProductMapper extends AbstractMapper<FavoriteProduct, FavoriteProductDto> {

    private final ModelMapper modelMapper;
    private final FavoriteRepository repository;

    @Autowired
    public FavoriteProductMapper(ModelMapper modelMapper, FavoriteRepository repository) {
        super(FavoriteProduct.class, FavoriteProductDto.class);
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(FavoriteProduct.class, FavoriteProductDto.class)
                .addMappings(m -> m.skip(FavoriteProductDto::setFavoriteId))
                .setPostConverter(toDtoConvert());
        mapper.createTypeMap(FavoriteProductDto.class, FavoriteProduct.class)
                .addMappings(m -> m.skip(FavoriteProduct::setFavorite))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(FavoriteProduct source, FavoriteProductDto destination) {
        destination.setFavoriteId(getId(source));
    }

    private Long getId(FavoriteProduct source) {
        return Objects.isNull(source) || Objects.isNull(source.getId())
                ? null
                : (Objects.isNull(source.getFavorite())
                    ? null
                    : source.getFavorite().getId());

    }

    @Override
    public void mapSpecificFields(FavoriteProductDto source, FavoriteProduct destination) {
        destination.setFavorite(repository.findById(source.getFavoriteId()).orElse(null));
    }
}
