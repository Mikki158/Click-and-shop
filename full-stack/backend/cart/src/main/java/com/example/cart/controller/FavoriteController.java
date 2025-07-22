package com.example.cart.controller;

import com.example.cart.dto.favorite.FavoriteProductDto;
import com.example.cart.dto.UserDto;
import com.example.cart.service.AuthService;
import com.example.cart.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    FavoriteService favoriteService;
    AuthService authService;

    @PostMapping
    public ResponseEntity<Void> addToFavorite(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody FavoriteProductDto product) {

        //UserDto user = authService.verifyAuthentication(authHeader);

        favoriteService.addToFavorite(userId, product);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteProductDto>> getFavoriteProduct(
            @RequestHeader("X-User-Id") Long userId) {
        
        //UserDto user = authService.verifyAuthentication(authHeader);

        List<FavoriteProductDto> response = favoriteService.getProducts(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
