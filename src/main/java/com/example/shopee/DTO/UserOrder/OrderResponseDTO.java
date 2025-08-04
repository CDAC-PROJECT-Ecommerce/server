package com.example.shopee.DTO.UserOrder;

import com.example.shopee.DTO.Address.AddressDto;
import com.example.shopee.DTO.Address.AddressResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;
    private List<OrderItemDTO> items;
    private AddressResponseDTO address;
}
