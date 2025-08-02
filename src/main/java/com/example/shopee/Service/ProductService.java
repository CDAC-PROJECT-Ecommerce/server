package com.example.shopee.Service;

import java.util.List;

import com.example.shopee.DTO.Product.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(Long categoryId);
    List<ProductDto> getProductsBySeller(Long sellerId);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}