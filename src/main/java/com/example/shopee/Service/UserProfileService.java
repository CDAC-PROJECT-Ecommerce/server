package com.example.shopee.Service;

import org.springframework.http.ResponseEntity;

public interface UserProfileService {
    ResponseEntity<?> fetchUserProfile(String username);
}
