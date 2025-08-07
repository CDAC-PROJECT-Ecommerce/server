package com.example.shopee.Controller;

import com.example.shopee.DTO.Cart.CartRequestDto;
import com.example.shopee.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(Authentication authentication, @RequestBody CartRequestDto cartRequestDto){
        return ResponseEntity.ok(cartService.addToCart(authentication.getName(), cartRequestDto.getProductId(), 1));
    }

    @GetMapping("/fetchCart")
    public ResponseEntity<?> fetchCart(Authentication authentication){
        return ResponseEntity.ok(cartService.getCartByUsername(authentication.getName()));
    }

    @PutMapping("/changeQuantity")
    public ResponseEntity<?> incrementQuantity(Authentication authentication, @RequestBody CartRequestDto cartRequestDto){
        System.out.println(cartRequestDto.getProductId()+" "+cartRequestDto.getValue());
        return ResponseEntity.ok(cartService.changeQuantity(authentication.getName(), cartRequestDto.getProductId(), cartRequestDto.getValue()));
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeItem(Authentication authentication,@PathVariable Long id){
        System.out.println(authentication.getName()+" "+id);
        return ResponseEntity.ok(cartService.removeItem(authentication.getName(),id));
    }
}
