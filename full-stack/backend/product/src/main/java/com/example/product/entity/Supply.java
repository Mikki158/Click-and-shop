package com.example.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Supply")
public class Supply extends AbstractEntity{

    private Product product;
    private Warehouse warehouse;
    private int quantity;

    @ManyToOne
    public Product getProduct() {
        return product;
    }

    @ManyToOne
    public Warehouse getWarehouse() {
        return warehouse;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }
}
