package com.example.cart.repository;

import com.example.cart.entity.Cart;
import com.example.cart.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    List<CartProduct> findByCart(Cart cart);

    Optional<CartProduct> findByProductIdAndCart(Long productId, Cart cart);
}
