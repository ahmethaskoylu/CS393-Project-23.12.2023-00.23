package com.restaurant.service;

import com.restaurant.entity.Cart;

import java.util.List;

public interface CartService {
    Cart createCart(Cart cart, Long userId);

    Cart getCartById(Long id);

    List<Cart> getAllCarts();

    Cart addProductToCart(Long cartId, Long productId);

    void deleteCart(Cart cart);
}
