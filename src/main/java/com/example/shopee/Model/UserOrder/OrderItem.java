package com.example.shopee.Model.UserOrder;

import com.example.shopee.Model.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    private Order order;
}
