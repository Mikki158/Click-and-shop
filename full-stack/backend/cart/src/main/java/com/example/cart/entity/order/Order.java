package com.example.cart.entity.order;

import com.example.cart.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    private Long userId;
    private String status;
    private Long totalPrice;
    private Long pickupPointId;

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    @Column(name = "total_price")
    public Long getTotalPrice() {
        return totalPrice;
    }

    @Column(name = "pickup_point_id")
    public Long getPickupPointId() {
        return pickupPointId;
    }

    public Order(Long userId, Long pickupPointId) {
        this.userId = userId;
        this.pickupPointId = pickupPointId;
        this.status = "В обработке";
        this.totalPrice = 0L;
    }
}
