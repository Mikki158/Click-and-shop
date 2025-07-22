package com.example.cart.repository.order;

import com.example.cart.entity.order.CompleteOrder;
import com.example.cart.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompleteOrderRepository extends JpaRepository<CompleteOrder, Long> {

    List<CompleteOrder> getCompleteOrdersByUserId(Long userId);

    List<CompleteOrder> findByUserId(Long userId);
}
