package com.example.shopee.Serviceimpl;

import com.example.shopee.DTO.Payment.PaymentRequestDto;
import com.example.shopee.DTO.Payment.PaymentVerificationRequestDto;
import com.example.shopee.Exception.ResourceNotFoundException;
import com.example.shopee.Model.UserOrder.OrderPaymentStatus;
import com.example.shopee.Model.UserOrder.Orders;
import com.example.shopee.Repo.UserOrder.OrderRepo;
import com.example.shopee.Service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepo orderRepo;
    @Value("${razorpay.key_id}")
    private String apiKey;

    @Value("${razorpay.key_secret}")
    private String apiSecret;

    @Override
    public ResponseEntity<?> makePayment(PaymentRequestDto paymentRequestDto) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);


            Orders order = orderRepo.findById(paymentRequestDto.getOrderId()).orElseThrow(()->  new ResourceNotFoundException("Order not found"));

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", order.getTotalAmount()  * 1000);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "Pay00"+paymentRequestDto.getOrderId());
            orderRequest.put("payment_capture",1);

            Order razorpayOrder = razorpayClient.orders.create(orderRequest);
            order.setRazorpayOrderId(razorpayOrder.get("id"));
            orderRepo.save(order);

            Map<String,Object> response = new HashMap<>();
            response.put("orderId",razorpayOrder.get("id"));
            response.put("amount",razorpayOrder.get("amount"));
            response.put("currency","INR");
            response.put("key","rzp_test_l7Q7HVqRLk6SQW");

            System.out.println(response);

            return ResponseEntity.ok(response);
        } catch (RazorpayException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Payment failed to initiate");
            error.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @Override
    public ResponseEntity<?> verifyPayment(PaymentVerificationRequestDto paymentVerificationRequestDto) {

        try{
            Orders order = orderRepo.findById(paymentVerificationRequestDto.getOrderId()).orElseThrow(()-> new ResourceNotFoundException("Order not found"));

            JSONObject attributes = new JSONObject(Map.of(
                    "razorpay_order_id", paymentVerificationRequestDto.getRazorpayOrderId(),
                    "razorpay_payment_id", paymentVerificationRequestDto.getRazorpayPaymentId(),
                    "razorpay_signature",paymentVerificationRequestDto.getRazorpaySignature()
            ));
            boolean isSignatureValid = Utils.verifyPaymentSignature(attributes, apiSecret);

            if (!isSignatureValid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature. Payment verification failed.");
            }

            order.setPaymentStatus(OrderPaymentStatus.PAID);

            orderRepo.save(order);
            return ResponseEntity.ok("Payment successfull");


        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying payment.");
        }
    }

    @Override
    public ResponseEntity<?> failurePayment(PaymentVerificationRequestDto paymentVerificationRequestDto) {
        Orders order = orderRepo.findById(paymentVerificationRequestDto.getOrderId()).orElseThrow(()-> new ResourceNotFoundException("Order not found"));

        order.setPaymentStatus(OrderPaymentStatus.FAILED);
        orderRepo.save(order);
        return ResponseEntity.ok("Payment failed");
    }
}
