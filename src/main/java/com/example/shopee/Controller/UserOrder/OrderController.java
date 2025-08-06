package com.example.shopee.Controller.UserOrder;

import com.example.shopee.DTO.UserOrder.OrderRequestDTO;
import com.example.shopee.Service.UserOrder.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/userorder")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO, Authentication authentication){
        System.out.println(authentication.getName());
        return orderService.placeOrder(orderRequestDTO, authentication.getName());
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrderByUser(Authentication authentication){
        return orderService.getOrdersByUsername(authentication.getName());
    }
}
