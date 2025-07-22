package com.example.cart.entity.order;

import com.example.cart.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complete_order_prouct")
public class CompleteOrderProuct extends AbstractEntity {

    private Long productId;
    private int quantity;
    private Long price;
    private CompleteOrder completeOrder;

    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    @ManyToOne
    @JoinColumn(name = "complete_order_id", referencedColumnName = "id", nullable = false)
    public CompleteOrder getCompleteOrder() {
        return completeOrder;
    }
}
