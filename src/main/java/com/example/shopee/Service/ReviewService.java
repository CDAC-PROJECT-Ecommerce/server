package com.example.shopee.Service;

import com.example.shopee.DTO.Review.ReviewDto;
import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviewsByProduct(Long productId);
    List<ReviewDto> getAllReviewsByCustomer(Long customerId);
    ReviewDto updateReview(Long id, ReviewDto reviewDto);
    void deleteReview(Long id);
    boolean hasCustomerReviewedProduct(Long customerId, Long productId, Long orderId);
}
