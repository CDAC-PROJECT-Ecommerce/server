package com.example.shopee.Controller;

import com.example.shopee.Service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/fetchByUsername")
    public ResponseEntity<?> fetchUser(Authentication authentication){
        return userProfileService.fetchUserProfile(authentication.getName());
    }
}
