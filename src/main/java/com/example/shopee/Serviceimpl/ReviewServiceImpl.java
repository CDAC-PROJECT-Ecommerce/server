package com.example.shopee.Serviceimpl;

import com.example.shopee.DTO.Review.ReviewDto;
import com.example.shopee.Exception.ResourceNotFoundException;
import com.example.shopee.Model.Product;
import com.example.shopee.Model.Review;
import com.example.shopee.Repo.ProductRepository;
import com.example.shopee.Repo.ReviewRepository;
import com.example.shopee.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        Product product = productRepository.findById(reviewDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + reviewDto.getProductId()));

        boolean alreadyReviewed = reviewRepository.existsByCustomerIdAndProductIdAndOrderId(
                reviewDto.getCustomerId(), reviewDto.getProductId(), reviewDto.getOrderId());

        if (alreadyReviewed) {
            throw new IllegalStateException("You have already reviewed this product");
        }

        Review review = new Review();
        review.setProduct(product);
        review.setCustomerId(reviewDto.getCustomerId());
        review.setOrderId(reviewDto.getOrderId());
        review.setRating(reviewDto.getRating());
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setReviewText(reviewDto.getReviewText());
        review.setImageUrls(reviewDto.getImageUrls());
        review.setIsVerifiedPurchase(true); // or add actual logic if needed
        review.setStatus(Review.Status.PENDING);

        Review saved = reviewRepository.save(review);
        return modelMapper.map(saved, ReviewDto.class);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(r -> modelMapper.map(r, ReviewDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
    }

    @Override
    public List<ReviewDto> getAllReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(r -> modelMapper.map(r, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getAllReviewsByCustomer(Long customerId) {
        return reviewRepository.findByCustomerId(customerId)
                .stream()
                .map(r -> modelMapper.map(r, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        if (reviewDto.getRating() != null) existing.setRating(reviewDto.getRating());
        if (reviewDto.getReviewTitle() != null) existing.setReviewTitle(reviewDto.getReviewTitle());
        if (reviewDto.getReviewText() != null) existing.setReviewText(reviewDto.getReviewText());
        if (reviewDto.getImageUrls() != null) existing.setImageUrls(reviewDto.getImageUrls());
        if (reviewDto.getIsVerifiedPurchase() != null) existing.setIsVerifiedPurchase(reviewDto.getIsVerifiedPurchase());
        if (reviewDto.getStatus() != null) existing.setStatus(reviewDto.getStatus());

        Review updated = reviewRepository.save(existing);
        return modelMapper.map(updated, ReviewDto.class);
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Override
    public boolean hasCustomerReviewedProduct(Long customerId, Long productId, Long orderId) {
        return reviewRepository.existsByCustomerIdAndProductIdAndOrderId(customerId, productId, orderId);
    }
}
