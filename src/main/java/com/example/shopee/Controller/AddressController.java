package com.example.shopee.Controller;

import com.example.shopee.DTO.Address.AddressDto;
import com.example.shopee.Service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<?> addAddress(@RequestBody @Valid AddressDto addressDto, Authentication authentication) {
        return addressService.addAddress(addressDto, authentication.getName());
    }

    @GetMapping
    public ResponseEntity<?> getAddress(Authentication authentication) {
        return addressService.getUserAddresses(authentication.getName());
    }

    @PutMapping("/default/{id}")
    public ResponseEntity<?> setDefaultAddress(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(addressService.setDefaultAddress(id, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id, Authentication authentication) {
        return addressService.deleteAddress(id, authentication.getName());
    }
}
