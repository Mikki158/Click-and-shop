package com.example.cart.mapper;

import com.example.cart.dto.CartProductDto;
import com.example.cart.entity.Cart;
import com.example.cart.entity.CartProduct;
import com.example.cart.repository.CartProductRepository;
import com.example.cart.repository.CartRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CartProductMapper extends AbstractMapper<CartProduct, CartProductDto>{

    private final ModelMapper mapper;
    private final CartRepository repository;

    @Autowired
    public CartProductMapper(ModelMapper mapper, CartRepository repository) {
        super(CartProduct.class, CartProductDto.class);
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(CartProduct.class, CartProductDto.class)
                .addMappings(m -> m.skip(CartProductDto::setCartId))
                .setPostConverter(toDtoConvert());
        mapper.createTypeMap(CartProductDto.class, CartProduct.class)
                .addMappings(m -> m.skip(CartProduct::setCart))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(CartProduct source, CartProductDto destination) {
        destination.setCartId(getId(source));
    }

    private Long getId(CartProduct source) {
        return Objects.isNull(source) || Objects.isNull(source.getId())
                ? null
                : (Objects.isNull(source.getCart())
                    ? null
                    : source.getCart().getId());
    }

    @Override
    public void mapSpecificFields(CartProductDto source, CartProduct destination) {
        destination.setCart(repository.findById(source.getCartId()).orElse(null));
    }
}
