package com.example.cart.entity.favorite;

import com.example.cart.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favorite_product")
public class FavoriteProduct extends AbstractEntity {

    private Long productId;
    private Favorite favorite;

    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    @ManyToOne
    @JoinColumn(name = "favorite_id", referencedColumnName = "id", nullable = false)
    public Favorite getFavorite() {
        return favorite;
    }
}
