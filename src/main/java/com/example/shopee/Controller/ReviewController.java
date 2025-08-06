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
            if (!authentication.getName().equals(reviewDto.getCustomerId().toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only create reviews for yourself");
            }
            
            ReviewDto createdReview = reviewService.createReview(reviewDto);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating review");
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
            return ResponseEntity.internalServerError().body("Error fetching reviews");
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getReviewsByCustomer(@PathVariable Long customerId, Authentication authentication) {
        try {
            // Verify the authenticated user matches the requested customerId
            if (!authentication.getName().equals(customerId.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only view your own reviews");
            }
            
            List<ReviewDto> reviews = reviewService.getAllReviewsByCustomer(customerId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching reviews");
        }
    }

    @GetMapping("/has-reviewed")
    public ResponseEntity<?> hasCustomerReviewedProduct(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam(required = false) Long orderId,
            Authentication authentication) {
        try {
            // Verify the authenticated user matches the requested customerId
            if (!authentication.getName().equals(customerId.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
            }
            
            boolean hasReviewed = reviewService.hasCustomerReviewedProduct(customerId, productId, orderId);
            return ResponseEntity.ok(hasReviewed);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error checking review status");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDto reviewDto,
            Authentication authentication) {
        try {
            // Verify the authenticated user matches the review's customerId
            ReviewDto existingReview = reviewService.getReviewById(id);
            if (!authentication.getName().equals(existingReview.getCustomerId().toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own reviews");
            }
            
            ReviewDto updatedReview = reviewService.updateReview(id, reviewDto);
            return ResponseEntity.ok(updatedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating review");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id, Authentication authentication) {
        try {
            // Verify the authenticated user matches the review's customerId
            ReviewDto existingReview = reviewService.getReviewById(id);
            if (!authentication.getName().equals(existingReview.getCustomerId().toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own reviews");
            }
            
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting review");
        }
    }
}