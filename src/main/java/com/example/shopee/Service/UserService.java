package com.example.shopee.Service;

import com.example.shopee.Config.UserDetailService;
import com.example.shopee.DTO.Auth.AuthRequest;
import com.example.shopee.DTO.Auth.AuthResponse;
import com.example.shopee.JWT.JWTService;
import com.example.shopee.Model.User;
import com.example.shopee.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    public ResponseEntity<?> registerUser(User user) {
        Map<String, String> response = new HashMap<>();

        if (userRepo.existsByUsername(user.getUsername())) {
            response.put("message", "Username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        response.put("token", token);
        response.put("username", user.getName());
        response.put("role",user.getRole().name());

        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> fetchUser(String token) {
        String username = jwtService.extractUsername(token);
        Map<String, String> response = new HashMap<>();
        if (username != null) {
            User user = userRepo.findByUsername(username).orElseThrow();
            response.put("username", user.getName());
            response.put("role", user.getRole().name());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    public ResponseEntity<?> loginUser(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
            User tempUser = userRepo.findByUsername(userDetails.getUsername()).orElseThrow();
            String token = jwtService.generateToken(userDetails.getUsername(), tempUser.getRole().name());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", tempUser.getName());
            response.put("role",tempUser.getRole().name());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}
