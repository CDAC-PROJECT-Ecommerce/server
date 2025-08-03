package com.example.shopee.Service;

import com.example.shopee.DTO.Cart.CartDto;

public interface CartService {
    CartDto addToCart(String token, Long productId, int quantity);
    CartDto getCartByUsername(String username);
    CartDto changeQuantity(String username, Long productId, int value);

    CartDto removeItem(String name, Long productId);
}
