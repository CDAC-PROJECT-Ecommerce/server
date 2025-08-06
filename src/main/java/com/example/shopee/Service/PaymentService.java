package com.example.shopee.Service;

import com.example.shopee.DTO.Payment.PaymentRequestDto;
import com.example.shopee.DTO.Payment.PaymentVerificationRequestDto;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<?> makePayment(PaymentRequestDto paymentRequestDto);

    ResponseEntity<?> verifyPayment(PaymentVerificationRequestDto paymentVerificationRequestDto);
    ResponseEntity<?> failurePayment(PaymentVerificationRequestDto paymentVerificationRequestDto);

}
