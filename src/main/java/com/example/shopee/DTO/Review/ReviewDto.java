package com.example.shopee.DTO.Review;

import com.example.shopee.Model.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Long id;
    private Long productId;
    private Long customerId;
    private Long orderId;
    private Integer rating;
    private String reviewTitle;
    private String reviewText;
    private String imageUrls;
    private Boolean isVerifiedPurchase;
    private Review.Status status;
}