package com.example.shopee.DTO.Review;

import com.example.shopee.Model.Review;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Long id;

    @NotNull(message = "productId is required")
    private Long productId;

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotNull(message = "orderId is required")
    private Long orderId;

    @NotNull(message = "rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @NotBlank(message = "reviewTitle is required")
    private String reviewTitle;

    @NotBlank(message = "reviewText is required")
    private String reviewText;

    private String imageUrls;

    private Boolean isVerifiedPurchase = false;

    private Review.Status status = Review.Status.PENDING;
}
