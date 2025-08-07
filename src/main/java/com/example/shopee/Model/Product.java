package com.example.shopee.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Column(name = "seller_id")
    private Long sellerId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name",  length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock_quantity",  columnDefinition = "INT DEFAULT 0")
    private Integer stockQuantity = 0;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('active', 'inactive', 'out_of_stock') DEFAULT 'active'")
    private Status status = Status.active;

    public enum Status {
        active, inactive, out_of_stock
    }
}