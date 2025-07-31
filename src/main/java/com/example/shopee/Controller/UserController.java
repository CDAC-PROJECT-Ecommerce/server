package com.example.shopee.Controller;

import com.example.shopee.DTO.Auth.AuthRequest;
import com.example.shopee.DTO.Auth.AuthResponse;
import com.example.shopee.DTO.Auth.RegisterAuthRequest;
import com.example.shopee.Model.User;
import com.example.shopee.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterAuthRequest registerAuthRequest) {
        User user = new User();
        user.setName(registerAuthRequest.getUsername());
        user.setUsername(registerAuthRequest.getEmail());
        user.setPassword(registerAuthRequest.getPassword());
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return userService.loginUser(authRequest);
    }

    @PostMapping("/fetchUsernameAndRole")
    public ResponseEntity<?> fetchUser(@RequestBody AuthResponse authResponse){
        return userService.fetchUser(authResponse.getToken());
    }
}


