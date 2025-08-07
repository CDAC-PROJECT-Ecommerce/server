package com.example.shopee.Admin.Controller;

import com.example.shopee.Admin.DTO.OrderStatusUpdateRequest;
import com.example.shopee.Admin.Service.AdminOrderService;
import com.example.shopee.DTO.UserOrder.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        System.out.println("Order porcesslsajf");
        return adminOrderService.getAllOrders();
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {

        return  adminOrderService.updateOrderStatus(orderId, request);

    }
}
