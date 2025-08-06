package com.example.shopee.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.razorpay.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    @PostMapping("/create-order")
    public String createOrder() throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", 50000); // amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_11");

        Order order = razorpay.orders.create(orderRequest);
        return order.toString();
    }
}