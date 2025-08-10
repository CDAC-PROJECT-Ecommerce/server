package com.example.shopee.DTO.Cart;

import lombok.Data;

@Data
public class CartItemDto {
    private long productId;
    private String productName;
    private int quantity;
    private double price;
    private String imageUrl;
}
