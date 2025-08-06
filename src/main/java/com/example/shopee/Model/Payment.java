package com.example.shopee.Model;

import com.example.shopee.Model.UserOrder.Orders;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User user;
    @ManyToOne
    private Orders order;
    private boolean paymentStatus;
}
