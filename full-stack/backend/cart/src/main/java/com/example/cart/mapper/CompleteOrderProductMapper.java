package com.example.cart.mapper;

import com.example.cart.dto.order.CompleteOrderProductDto;
import com.example.cart.entity.order.CompleteOrderProuct;
import com.example.cart.repository.order.CompleteOrderRepository;
import com.example.cart.repository.order.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CompleteOrderProductMapper extends AbstractMapper<CompleteOrderProuct, CompleteOrderProductDto> {

    private final ModelMapper mapper;
    private final CompleteOrderRepository repository;

    @Autowired
    public CompleteOrderProductMapper(ModelMapper mapper, CompleteOrderRepository repository) {
        super(CompleteOrderProuct.class, CompleteOrderProductDto.class);
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(CompleteOrderProuct.class, CompleteOrderProductDto.class)
                .addMappings(m -> m.skip(CompleteOrderProductDto::setCompleteOrderId))
                .setPostConverter(toDtoConvert());
        mapper.createTypeMap(CompleteOrderProductDto.class, CompleteOrderProuct.class)
                .addMappings(m -> m.skip(CompleteOrderProuct::setCompleteOrder))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(CompleteOrderProuct source, CompleteOrderProductDto destination) {
        destination.setCompleteOrderId(getId(source));
    }

    private Long getId(CompleteOrderProuct source) {
        return Objects.isNull(source) || Objects.isNull(source.getId())
                ? null
                : (Objects.isNull(source.getCompleteOrder())
                    ? null
                    : source.getCompleteOrder().getId());
    }

    @Override
    public void mapSpecificFields(CompleteOrderProductDto source, CompleteOrderProuct destination) {
        destination.setCompleteOrder(repository.findById(source.getCompleteOrderId()).orElse(null));
    }
}
