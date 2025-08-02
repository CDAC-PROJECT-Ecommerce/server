package com.example.shopee.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopee.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}