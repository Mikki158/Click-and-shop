package com.example.cart.service;

import java.util.List;

import com.example.cart.dto.favorite.FavoriteProductDto;

public interface FavoriteService {

    void addToFavorite(Long userId, FavoriteProductDto product);

    List<FavoriteProductDto> getProducts(Long UserId);
}
