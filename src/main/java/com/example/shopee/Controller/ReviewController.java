package com.example.shopee.Controller;

import com.example.shopee.DTO.Review.ReviewDto;
import com.example.shopee.Service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto createdReview = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        ReviewDto reviewDto = reviewService.getReviewById(id);
        return ResponseEntity.ok(reviewDto);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProduct(@PathVariable Long productId) {
        List<ReviewDto> reviews = reviewService.getAllReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByCustomer(@PathVariable Long customerId) {
        List<ReviewDto> reviews = reviewService.getAllReviewsByCustomer(customerId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/has-reviewed")
    public ResponseEntity<Boolean> hasCustomerReviewedProduct(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam(required = false) Long orderId) {
        boolean hasReviewed = reviewService.hasCustomerReviewedProduct(customerId, productId, orderId);
        return ResponseEntity.ok(hasReviewed);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(id, reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}