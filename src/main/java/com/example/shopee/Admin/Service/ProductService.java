package com.example.shopee.Admin.Service;

import com.example.shopee.Admin.DTO.ProductRequestDTO;
import com.example.shopee.Admin.Repo.CategoryRepo;
import com.example.shopee.Exception.ResourceNotFoundException;
import com.example.shopee.Model.Category;
import com.example.shopee.Model.Product;
import com.example.shopee.Repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepo categoryRepository;
    private final ImageUploadService imageUploadService;

    public Product addProduct(ProductRequestDTO request) {
        Category category = categoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategory()));
        String imageUrl="";
        try{
            imageUrl = imageUploadService.uploadImage(request.getImage());
        } catch (IOException e) {
            throw new ResourceNotFoundException("Image upload failed");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product.setImageUrl(imageUrl);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setStockQuantity(request.getStockQuantity());

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product updateProduct(Long id, ProductRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());
        existing.setStockQuantity(dto.getStockQuantity());
        // Set image if updating image logic is needed
        existing.setUpdatedAt(new Date());
        return productRepository.save(existing);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

}
