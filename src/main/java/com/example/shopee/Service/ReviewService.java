package com.example.shopee.Service;

import java.util.List;

import com.example.shopee.DTO.Review.ReviewDto;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviewsByProduct(Long productId);
    List<ReviewDto> getAllReviewsByCustomer(Long customerId);
    ReviewDto updateReview(Long id, ReviewDto reviewDto);
    void deleteReview(Long id);
    boolean hasCustomerReviewedProduct(Long customerId, Long productId, Long orderId);
}