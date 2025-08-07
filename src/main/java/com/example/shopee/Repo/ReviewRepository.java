package com.example.shopee.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopee.Model.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
    List<Review> findByCustomerId(Long customerId);
    boolean existsByCustomerIdAndProductIdAndOrderId(Long customerId, Long productId, Long orderId);
}