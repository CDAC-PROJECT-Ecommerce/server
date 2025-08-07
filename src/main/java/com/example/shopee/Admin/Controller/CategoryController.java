package com.example.shopee.Admin.Controller;

import com.example.shopee.Admin.Service.CategoryService;
import com.example.shopee.Model.Category;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    // ✅ GET all categories
    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // ✅ POST new category
    @PostMapping
    public ResponseEntity<Category> addCategory(@Valid @RequestBody CategoryRequest request) {
        Category created = categoryService.addCategory(request.getName());
        return ResponseEntity.ok(created);
    }

    @Data
    static class CategoryRequest {
        private String name;
    }
}
