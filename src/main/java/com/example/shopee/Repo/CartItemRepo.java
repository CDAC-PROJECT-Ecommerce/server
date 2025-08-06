package com.example.shopee.Repo;

import com.example.shopee.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    
}
