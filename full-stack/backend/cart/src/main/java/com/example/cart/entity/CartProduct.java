package com.example.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProduct extends AbstractEntity {

    private Long productId;
    private int quantity;
    private Cart cart;

    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    public Cart getCart() {
        return cart;
    }
}
