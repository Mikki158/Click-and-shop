package com.example.cart.repository.order;

import com.example.cart.entity.order.Order;
import com.example.cart.entity.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> getOrderProductsByOrder(Order order);
}
