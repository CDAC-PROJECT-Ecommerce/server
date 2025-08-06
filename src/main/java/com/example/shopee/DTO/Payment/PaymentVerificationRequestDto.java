package com.example.shopee.DTO.Payment;

import lombok.Data;

@Data
public class PaymentVerificationRequestDto {
    private Long orderId;
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
    private String reason;
}
