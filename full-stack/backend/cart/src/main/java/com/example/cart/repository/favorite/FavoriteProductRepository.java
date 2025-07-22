package com.example.cart.repository.favorite;

import com.example.cart.entity.favorite.Favorite;
import com.example.cart.entity.favorite.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

    Optional<FavoriteProduct> findByProductIdAndFavorite(Long productId, Favorite favorite);

    List<FavoriteProduct> findByFavorite(Favorite favorite);
}
