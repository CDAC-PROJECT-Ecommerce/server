package com.example.shopee.DTO.UserOrder;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private Long addressId;
    private List<OrderItemDTO> items;
    private Double totalAmount;
}
