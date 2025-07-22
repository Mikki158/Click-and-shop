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
@Table(name = "pickup_point")
public class PickupPoint extends AbstractEntity{

    private String address;
    private String phoneNumber;

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
