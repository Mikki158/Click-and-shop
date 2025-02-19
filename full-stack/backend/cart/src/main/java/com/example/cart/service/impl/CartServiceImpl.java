package com.example.cart.service.impl;

import com.example.cart.dto.CartDto;
import com.example.cart.dto.CartProductDto;
import com.example.cart.dto.UserDto;
import com.example.cart.entity.Cart;
import com.example.cart.entity.CartProduct;
import com.example.cart.mapper.CartMapper;
import com.example.cart.mapper.CartProductMapper;
import com.example.cart.repository.CartProductRepository;
import com.example.cart.repository.CartRepository;
import com.example.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    CartProductRepository cartProductRepository;

    CartProductMapper cartProductMapper;
    CartMapper cartMapper;

    @Override
    public UserDto verifyAuthentication(String authHeader) {

        String url = "https://click-and-shop.ru/api/auth/userInfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<UserDto>() {}
        );

        UserDto responseBody = response.getBody();

        return responseBody;
    }

    @Override
    public List<CartProductDto> getProducts(Long userId) {

        Cart cart = cartRepository.findByUserId(userId);

        List<CartProduct> products = cartProductRepository.findByCart(cart);

        List<CartProductDto> response = new ArrayList<>();

        for (CartProduct product : products) {

            CartProductDto cartProductDto = cartProductMapper.toDto(product);
            response.add(cartProductDto);
        }

        return response;
    }

    @Override
    public String addToCart(Long userId, CartProductDto product) {


        System.out.println("USER_ID = " + userId);

        Cart cart = cartRepository.findByUserId(userId);

        product.setCartId(cart.getId());

        System.out.println("Cart_id = " + cart.getId());
        System.out.println("ProductId = " + product.getProductId());
        System.out.println("Quantity = " + product.getQuantity());

        Optional<CartProduct> existingProduct = cartProductRepository.findByProductIdAndCart(
                product.getProductId(),
                cart);

        if (existingProduct.isPresent()) {
            CartProduct productToUpdate = existingProduct.get();
            productToUpdate.setQuantity(productToUpdate.getQuantity() + 1);
            cartProductRepository.save(productToUpdate);
        } else {
            product.setQuantity(1);
            cartProductRepository.save(cartProductMapper.toEntity(product));
        }

        return "Товар был добавлен в корзину";
    }

    @Override
    public CartDto createCart(Long userId) {

        if(cartRepository.existsByUserId(userId)) {
            throw new RuntimeException("Корзина уже существует");
        }

        Cart cart = new Cart(userId);

        Cart saveCart = cartRepository.save(cart);

        return cartMapper.toDto(saveCart);
    }

    @Override
    public String decreaseQuantity(Long userId, Long productId) {

        Cart cart = cartRepository.findByUserId(userId);

        Optional<CartProduct> existingProduct = cartProductRepository.findByProductIdAndCart(
                productId, cart);

        if (existingProduct.isPresent() && existingProduct.get().getQuantity() > 1) {
            CartProduct product = existingProduct.get();
            product.setQuantity(product.getQuantity() - 1);
            cartProductRepository.save(product);
            return "Количество уменьшилось на 1";
        } else {
            throw new RuntimeException("Такого товара нет");
        }
    }

    @Override
    public String removeFromCart(Long userId, Long productId) {

        Cart cart = cartRepository.findByUserId(userId);

        Optional<CartProduct> existingProduct = cartProductRepository.findByProductIdAndCart(
                productId, cart);

        if (existingProduct.isPresent()) {
            CartProduct product = existingProduct.get();
            cartProductRepository.delete(product);
            return "Товар был удален";
        } else {
            throw new RuntimeException("Такого товара нет");
        }
    }
}
