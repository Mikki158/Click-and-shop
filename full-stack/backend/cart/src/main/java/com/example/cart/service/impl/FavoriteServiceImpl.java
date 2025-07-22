package com.example.cart.service.impl;

import com.example.cart.dto.favorite.FavoriteProductDto;
import com.example.cart.entity.cart.Cart;
import com.example.cart.entity.favorite.Favorite;
import com.example.cart.entity.favorite.FavoriteProduct;
import com.example.cart.repository.favorite.FavoriteProductRepository;
import com.example.cart.repository.favorite.FavoriteRepository;
import com.example.cart.service.FavoriteService;
import com.example.cart.mapper.FavoriteProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    FavoriteRepository favoriteRepository;
    FavoriteProductRepository favoriteProductRepository;
    FavoriteProductMapper favoriteProductMapper;

    @Override
    public void addToFavorite(Long userId, FavoriteProductDto product) {

        Optional<Favorite> existFavorite  = favoriteRepository.findByUserId(userId);
        Favorite favorite;

        if (existFavorite.isPresent()) {
            System.out.println("Корзина найдена для возврата товаров");
            favorite = existFavorite.get();
        } else {
            System.out.println("Корзина НЕ найдена для возврата товаров");
            favorite = favoriteRepository.save(new Favorite(userId));
        }

        product.setFavoriteId(favorite.getId());

        Optional<FavoriteProduct> existingProduct = favoriteProductRepository.findByProductIdAndFavorite(
                product.getProductId(),
                favorite
        );

        if(existingProduct.isPresent()) {
            FavoriteProduct favoriteProduct = existingProduct.get();
            favoriteProductRepository.delete(favoriteProduct);
        } else {
            favoriteProductRepository.save(favoriteProductMapper.toEntity(product));
        }
    }

    @Override
    public List<FavoriteProductDto> getProducts(Long userId) {

        Optional<Favorite> existFavorite  = favoriteRepository.findByUserId(userId);
        Favorite favorite;

        if (existFavorite.isPresent()) {
            System.out.println("Корзина найдена для возврата товаров");
            favorite = existFavorite.get();
        } else {
            System.out.println("Корзина НЕ найдена для возврата товаров");
            favorite = favoriteRepository.save(new Favorite(userId));
        }
        
        List<FavoriteProduct> products = favoriteProductRepository.findByFavorite(favorite);

        List<FavoriteProductDto> response = new ArrayList<>();

        for(FavoriteProduct product : products) {
            response.add(favoriteProductMapper.toDto(product));
        }

        return response;
    }
}
