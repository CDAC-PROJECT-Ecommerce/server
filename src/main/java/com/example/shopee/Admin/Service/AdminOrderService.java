package com.example.shopee.Admin.Service;

import com.example.shopee.Admin.DTO.OrderStatusUpdateRequest;
import com.example.shopee.DTO.Address.AddressResponseDTO;
import com.example.shopee.DTO.UserOrder.OrderItemDTO;
import com.example.shopee.DTO.UserOrder.OrderResponseDTO;
import com.example.shopee.Exception.ResourceNotFoundException;
import com.example.shopee.Model.UserOrder.Orders;
import com.example.shopee.Repo.UserOrder.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminOrderService {
private final OrderRepo   orderRepo;
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){

        List<Orders> orders = orderRepo.findAll();

        return ResponseEntity.ok(orders.stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setStatus(order.getStatus().name());
            dto.setPaymentStatus(order.getPaymentStatus().name());

            AddressResponseDTO addressResponseDTO = new AddressResponseDTO(order.getAddress());
            dto.setAddress(addressResponseDTO);

            List<OrderItemDTO> items = order.getItems().stream().map(item->{
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setProductId(item.getProduct().getId());
                orderItemDTO.setProductName(item.getProduct().getName());
                orderItemDTO.setQuantity(item.getQuantity());
                return orderItemDTO;
            }).toList();
            dto.setItems(items);

            return dto;
        }).toList());
    }

    public ResponseEntity<?> updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
        Orders order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(request.getStatus());
        Orders updated = orderRepo.save(order);
        return ResponseEntity.ok("Updated sucessfully");
    }
}
