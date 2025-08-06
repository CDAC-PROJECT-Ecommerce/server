package com.example.shopee.Serviceimpl;

import com.example.shopee.DTO.Profile.UserResponseDTO;
import com.example.shopee.Model.User;
import com.example.shopee.Repo.UserRepo;
import com.example.shopee.Service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepo userRepo;

    @Override
    public ResponseEntity<?> fetchUserProfile(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setFullName(user.getName());
        userResponseDTO.setEmail(user.getUsername());

        return ResponseEntity.ok(userResponseDTO);
    }
}
