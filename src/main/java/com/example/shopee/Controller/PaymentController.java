package com.example.shopee.Controller;

import com.example.shopee.DTO.Payment.PaymentRequestDto;
import com.example.shopee.DTO.Payment.PaymentVerificationRequestDto;
import com.example.shopee.Service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/createPayment")
    public ResponseEntity<?> createOrder(@RequestBody PaymentRequestDto paymentRequestDto, Authentication authentication) throws RazorpayException {
        System.out.println(authentication.getName());
        return paymentService.makePayment(paymentRequestDto);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequestDto paymentVerificationRequestDto){
         return paymentService.verifyPayment(paymentVerificationRequestDto);
    }

    @PostMapping("/failure")
    public ResponseEntity<?> paymentFailure(@RequestBody PaymentVerificationRequestDto paymentVerificationRequestDto){
        return paymentService.failurePayment(paymentVerificationRequestDto);
    }
}