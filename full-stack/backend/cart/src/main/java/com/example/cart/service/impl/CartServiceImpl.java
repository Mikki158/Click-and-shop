package com.example.cart.service.impl;

import com.example.cart.dto.cart.CartDto;
import com.example.cart.dto.cart.CartProductDto;
import com.example.cart.entity.cart.Cart;
import com.example.cart.entity.cart.CartProduct;
import com.example.cart.mapper.CartMapper;
import com.example.cart.mapper.CartProductMapper;
import com.example.cart.repository.cart.CartProductRepository;
import com.example.cart.repository.cart.CartRepository;
import com.example.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<CartProductDto> getProducts(Long userId) {

        System.out.println("Получение товаров");

        Optional<Cart> existCart  = cartRepository.findByUserId(userId);
        Cart cart;

        if (existCart.isPresent()) {
            System.out.println("Корзина найдена для возврата товаров");
            cart = existCart.get();
        } else {
            System.out.println("Корзина НЕ найдена для возврата товаров");
            cart = cartRepository.save(new Cart(userId));
        }

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

        Optional<Cart> existCart  = cartRepository.findByUserId(userId);
        Cart cart;

        if (existCart.isPresent()) {
            System.out.println("Корзина найдена для добавления товара в корзину");
            cart = existCart.get();
        } else {
            System.out.println("Корзина НЕ найдена для добавления товара в корзину");
            cart = cartRepository.save(new Cart(userId));
        }

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

        System.out.println("Создание корзины");

        Cart cart = new Cart(userId);

        Cart saveCart = cartRepository.save(cart);

        return cartMapper.toDto(saveCart);
    }

    @Override
    public String decreaseQuantity(Long userId, Long productId) {

        System.out.println("Уменьшение товаров в корзине");

        Optional<Cart> existCart  = cartRepository.findByUserId(userId);
        Cart cart;

        if (existCart.isPresent()) {
            System.out.println("Корзина найдена для уменьшения количества товаров");
            cart = existCart.get();
        } else {
            System.out.println("Корзина НЕ найдена для уменьшения количества товаров");
            cart = cartRepository.save(new Cart(userId));
        }

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

        System.out.println("Удаление товаров из корзины");

        Optional<Cart> existCart  = cartRepository.findByUserId(userId);
        Cart cart;

        if (existCart.isPresent()) {
            System.out.println("Корзина найдена для удаления товаров");
            cart = existCart.get();
        } else {
            System.out.println("Корзина НЕ найдена для удаления товаров");
            cart = cartRepository.save(new Cart(userId));
        }

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

    @Override
    public void clearCart(Long userId) {

        Optional<Cart> existCart  = cartRepository.findByUserId(userId);
        Cart cart;

        if (existCart.isPresent()) {
            System.out.println("Корзина найдена для удаления товаров");
            cart = existCart.get();
        } else {
            System.out.println("Корзина НЕ найдена для удаления товаров");
            return;
        }

        List<CartProduct> products = cartProductRepository.findByCart(cart);

        for (CartProduct product : products) {
            cartProductRepository.delete(product);
        }
    }
}
