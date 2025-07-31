package com.example.shopee.DTO.Auth;

import com.example.shopee.Model.UserRole;
import lombok.Data;

@Data
public class RegisterAuthRequest {
    private String email;
    private String username;
    private String password;
    private UserRole role;
}
