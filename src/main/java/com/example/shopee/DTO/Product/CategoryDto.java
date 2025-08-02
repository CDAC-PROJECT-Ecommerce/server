package com.example.shopee.DTO.Product;

import com.example.shopee.Model.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private Category.Status status;
}