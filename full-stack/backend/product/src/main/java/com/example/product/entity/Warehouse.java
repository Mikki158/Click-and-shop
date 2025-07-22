package com.example.product.entity;

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
@Table(name = "Warehouse")
public class Warehouse extends AbstractEntity{

    private String address;
    private Long capacity;

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Column(name = "capacity")
    public Long getCapacity() {
        return capacity;
    }
}
