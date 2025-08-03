package com.example.shopee.Service;

import com.example.shopee.DTO.Address.AddressDto;
import com.example.shopee.DTO.Address.AddressResponseDTO;
import com.example.shopee.Model.Address;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService {
    ResponseEntity<AddressResponseDTO> addAddress(AddressDto addressDto, String username);
    ResponseEntity<List<AddressResponseDTO>> getUserAddresses(String username);
    ResponseEntity<List<AddressResponseDTO>> deleteAddress(Long id, String username);
    List<AddressResponseDTO> setDefaultAddress(Long id, String username);
}
