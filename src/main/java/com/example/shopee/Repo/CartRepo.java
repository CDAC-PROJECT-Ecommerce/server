package com.example.shopee.Repo;

import com.example.shopee.Model.Cart;
import com.example.shopee.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUser(User user);
}

