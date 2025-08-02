package com.example.shopee.DTO.Product;

import com.example.shopee.Model.Product.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private Long sellerId;
    private Long categoryId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String imageUrl;
    private Status status;
}