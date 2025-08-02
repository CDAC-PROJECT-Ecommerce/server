package com.example.shopee.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review", 
       uniqueConstraints = @UniqueConstraint(
           name = "unique_customer_product_order", 
           columnNames = {"customer_id", "product_id", "order_id"}))
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "rating", nullable = false)
    private Integer rating;
    
    @Column(name = "review_title", length = 200)
    private String reviewTitle;
    
    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;
    
    @Column(name = "image_urls", columnDefinition = "TEXT")
    private String imageUrls;
    
    @Column(name = "is_verified_purchase", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isVerifiedPurchase = false;
    
    @Column(name = "helpful_count", columnDefinition = "INT DEFAULT 0")
    private Integer helpfulCount = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('pending', 'approved', 'rejected') DEFAULT 'pending'")
    private Status status = Status.pending;

    public enum Status {
        pending, approved, rejected
    }
}