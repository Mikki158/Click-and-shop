package com.example.cart.repository.order;

import com.example.cart.entity.order.CompleteOrder;
import com.example.cart.entity.order.CompleteOrderProuct;
import com.example.cart.entity.order.Order;
import com.example.cart.entity.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CompleteOrderProductRepository extends JpaRepository<CompleteOrderProuct, Long> {

    List<CompleteOrderProuct> getCompleteOrderProuctsByCompleteOrder(CompleteOrder order);

    @Query("SELECT DISTINCT cop.productId FROM CompleteOrderProuct cop WHERE cop.completeOrder.id IN :orderIds")
    Set<Long> findDistinctProductsByOrderIds(@Param("orderIds") List<Long> orderIds);

    List<CompleteOrderProuct> getOrderProductsByCompleteOrder(CompleteOrder completeOrder);
}
