package com.example.cart.entity.favorite;

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
@Table(name = "Favorite")
public class Favorite extends AbstractEntity {

    private Long userId;

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }
}
