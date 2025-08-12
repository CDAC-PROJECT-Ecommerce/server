package com.example.shopee.Admin.Controller;

import com.example.shopee.Admin.DTO.ProductRequestDTO;
import com.example.shopee.Admin.Service.ProductService;
import com.example.shopee.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("image") MultipartFile image,
            Authentication authentication
    ) {
        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setName(name);
        productRequest.setPrice(price);
        productRequest.setDescription(description);
        productRequest.setCategory(category);
        productRequest.setStockQuantity(stockQuantity);
        productRequest.setImage(image);

        Product savedProduct = productService.addProduct(productRequest);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("image") MultipartFile image
            ) {
        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setName(name);
        productRequest.setPrice(price);
        productRequest.setDescription(description);
        productRequest.setImage(image);
        productRequest.setStockQuantity(stockQuantity);
        Product updated = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}
