package com.example.shopee.Admin.DTO;

import com.example.shopee.Model.UserOrder.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    private OrderStatus status;

}
