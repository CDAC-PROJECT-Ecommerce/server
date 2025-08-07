package com.example.shopee.Serviceimpl;

import com.example.shopee.DTO.Review.ReviewDto;
import com.example.shopee.Exception.ResourceNotFoundException;
import com.example.shopee.Model.Product;
import com.example.shopee.Model.Review;
import com.example.shopee.Model.User;
import com.example.shopee.Repo.ProductRepository;
import com.example.shopee.Repo.ReviewRepository;
import com.example.shopee.Repo.UserRepo;
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
    private final UserRepo userRepo;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto, String username) {
        Product product = productRepository.findById(reviewDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + reviewDto.getProductId()));
        User user = userRepo.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Review review = modelMapper.map(reviewDto, Review.class);
        review.setProduct(product);
        review.setCustomerName(user.getName());
        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewDto.class);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return modelMapper.map(review, ReviewDto.class);
    }

    @Override
    public List<ReviewDto> getAllReviewsByProduct(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(review -> {
                    ReviewDto dto = modelMapper.map(review, ReviewDto.class);

                    userRepo.findById(review.getCustomerId())
                            .ifPresent(user -> dto.setCustomerName(user.getName())); // or user.getFirstName() + " " + user.getLastName()

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getAllReviewsByCustomer(Long customerId) {
        List<Review> reviews = reviewRepository.findByCustomerId(customerId);
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        reviewDto.setId(null);
        
        modelMapper.map(reviewDto, existingReview);
        Review updatedReview = reviewRepository.save(existingReview);
        return modelMapper.map(updatedReview, ReviewDto.class);
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