package com.example.shopee.Mapper;

import com.example.shopee.DTO.Address.AddressResponseDTO;
import com.example.shopee.DTO.UserOrder.OrderItemDTO;
import com.example.shopee.DTO.UserOrder.OrderResponseDTO;
import com.example.shopee.Model.UserOrder.Order;
import org.springframework.stereotype.Component;

import java.util.List;

public class OrderMapper {
    public static OrderResponseDTO convertToDto(Order order){
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());

        List<OrderItemDTO> itemDTOs = order.getItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            return itemDTO;
        }).toList();

        dto.setItems(itemDTOs);
        dto.setAddress(new AddressResponseDTO(order.getAddress()));

        return dto;
    }
}
