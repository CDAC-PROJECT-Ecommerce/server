package com.example.shopee.DTO.Cart;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    public Long cartId;
    private String username;
    private String imageUrl;
    private List<CartItemDto> items;

}
