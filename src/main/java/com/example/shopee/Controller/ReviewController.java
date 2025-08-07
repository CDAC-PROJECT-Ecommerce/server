package com.example.shopee.Controller;

import com.example.shopee.DTO.Review.ReviewDto;
import com.example.shopee.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDto reviewDto, Authentication authentication) {
        try {
            ReviewDto createdReview = reviewService.createReview(reviewDto,authentication.getName());
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already reviewed") || 
                e.getMessage().contains("duplicate") || 
                e.getMessage().contains("constraint")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("You have already reviewed this product");
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating review: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        try {
            ReviewDto reviewDto = reviewService.getReviewById(id);
            return ResponseEntity.ok(reviewDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getReviewsByProduct(@PathVariable Long productId) {
        try {
            List<ReviewDto> reviews = reviewService.getAllReviewsByProduct(productId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching reviews: " + e.getMessage());
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getReviewsByCustomer(@PathVariable Long customerId, Authentication authentication) {
        try {
            List<ReviewDto> reviews = reviewService.getAllReviewsByCustomer(customerId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching reviews: " + e.getMessage());
        }
    }

    @GetMapping("/has-reviewed")
    public ResponseEntity<?> hasCustomerReviewedProduct(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam(required = false) Long orderId,
            Authentication authentication) {
        try {
            boolean hasReviewed = reviewService.hasCustomerReviewedProduct(customerId, productId, orderId);
            return ResponseEntity.ok(hasReviewed);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error checking review status: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDto reviewDto,
            Authentication authentication) {
        try {
            ReviewDto updatedReview = reviewService.updateReview(id, reviewDto);
            return ResponseEntity.ok(updatedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating review: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id, Authentication authentication) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting review: " + e.getMessage());
        }
    }
}