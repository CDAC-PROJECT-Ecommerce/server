package com.example.shopee.Admin.Service;

import com.example.shopee.Admin.Repo.CategoryRepo;
import com.example.shopee.Model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepo categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new RuntimeException("Category already exists");
        }
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }
}
