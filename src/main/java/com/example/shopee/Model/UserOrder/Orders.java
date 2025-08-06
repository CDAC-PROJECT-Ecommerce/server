package com.example.shopee.Model.UserOrder;

import com.example.shopee.Model.Address;
import com.example.shopee.Model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    private Double totalAmount;

    @Enumerated(value=EnumType.STRING)
    private OrderStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "paymentStatus")
    private OrderPaymentStatus paymentStatus;

    private String razorpayOrderId;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();


}
