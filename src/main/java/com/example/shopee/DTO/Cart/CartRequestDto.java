package com.example.shopee.DTO.Cart;

import lombok.Data;

@Data
public class CartRequestDto {
    private String token;
    private Long productId;
    private int value;
}
