package com.example.shopee.Service.UserOrder;

import com.example.shopee.DTO.UserOrder.OrderRequestDTO;
import com.example.shopee.DTO.UserOrder.OrderResponseDTO;
import com.example.shopee.Model.UserOrder.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderResponseDTO> placeOrder(OrderRequestDTO orderRequestDTO,String username);
    ResponseEntity<List<OrderResponseDTO>> getOrdersByUsername(String username);
}
