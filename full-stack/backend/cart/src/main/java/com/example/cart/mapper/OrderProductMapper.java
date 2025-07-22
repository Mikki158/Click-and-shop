package com.example.cart.mapper;

import com.example.cart.dto.order.OrderProductDto;
import com.example.cart.entity.cart.CartProduct;
import com.example.cart.entity.order.OrderProduct;
import com.example.cart.repository.order.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderProductMapper extends AbstractMapper<OrderProduct, OrderProductDto> {

    private final ModelMapper mapper;
    private final OrderRepository repository;

    @Autowired
    public OrderProductMapper(ModelMapper mapper, OrderRepository repository) {
        super(OrderProduct.class, OrderProductDto.class);
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(OrderProduct.class, OrderProductDto.class)
                .addMappings(m -> m.skip(OrderProductDto::setOrderId))
                .setPostConverter(toDtoConvert());
        mapper.createTypeMap(OrderProductDto.class, OrderProduct.class)
                .addMappings(m -> m.skip(OrderProduct::setOrder))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(OrderProduct source, OrderProductDto destination) {
        destination.setOrderId(getId(source));
    }

    private Long getId(OrderProduct source) {
        return Objects.isNull(source) || Objects.isNull(source.getId())
                ? null
                : (Objects.isNull(source.getOrder())
                    ? null
                    : source.getOrder().getId());
    }

    @Override
    public void mapSpecificFields(OrderProductDto source, OrderProduct destination) {
        destination.setOrder(repository.findById(source.getOrderId()).orElse(null));
    }
}
