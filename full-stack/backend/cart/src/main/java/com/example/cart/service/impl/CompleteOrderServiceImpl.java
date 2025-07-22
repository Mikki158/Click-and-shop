package com.example.cart.service.impl;

import com.example.cart.dto.order.CompleteOrderDto;
import com.example.cart.dto.order.CompleteOrderProductDto;
import com.example.cart.entity.order.CompleteOrder;
import com.example.cart.entity.order.CompleteOrderProuct;
import com.example.cart.mapper.CompleteOrderMapper;
import com.example.cart.mapper.CompleteOrderProductMapper;
import com.example.cart.repository.order.CompleteOrderProductRepository;
import com.example.cart.repository.order.CompleteOrderRepository;
import com.example.cart.service.CompleteOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompleteOrderServiceImpl implements CompleteOrderService {

    CompleteOrderRepository completeOrderRepository;
    CompleteOrderProductRepository completeOrderProductRepository;
    CompleteOrderProductMapper completeOrderProductMapper;
    CompleteOrderMapper completeOrderMapper;

    @Override
    public List<CompleteOrderDto> getCompleteOrders(Long userId) {

        List<CompleteOrder> completeOrders = completeOrderRepository.findByUserId(userId);
        List<CompleteOrderDto> response = new ArrayList<>();

        for (CompleteOrder completeOrder : completeOrders) {
            response.add(completeOrderMapper.toDto(completeOrder));
        }

        return response;
    }

    @Override
    public CompleteOrderDto getCompleteOrder(Long completeOrderId) {
        return completeOrderMapper.toDto(completeOrderRepository.getReferenceById(completeOrderId));
    }

    @Override
    public List<CompleteOrderProductDto> getCompleteProduct(Long completeOrderId) {

        CompleteOrder completeOrder = completeOrderRepository.getReferenceById(completeOrderId);

        List<CompleteOrderProuct> products = completeOrderProductRepository.getOrderProductsByCompleteOrder(completeOrder);
        List<CompleteOrderProductDto> response = new ArrayList<>();

        for (CompleteOrderProuct completeProduct : products) {
            response.add(completeOrderProductMapper.toDto(completeProduct));
        }

        return response;
    }

    @Override
    public List<Long> getAllCompleteProducts(Long userId) {

        List<CompleteOrder> completeOrders = completeOrderRepository.getCompleteOrdersByUserId(userId);
        List<Long> completeOrder = completeOrders.stream()
                .map(CompleteOrder::getId)
                .collect(Collectors.toList());

        List<Long> response = completeOrderProductRepository.findDistinctProductsByOrderIds(completeOrder).stream().collect(Collectors.toList());

        return response;
    }
}
