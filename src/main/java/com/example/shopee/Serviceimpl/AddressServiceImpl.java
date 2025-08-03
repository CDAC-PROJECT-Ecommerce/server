package com.example.shopee.Serviceimpl;

import com.example.shopee.DTO.Address.AddressDto;
import com.example.shopee.DTO.Address.AddressResponseDTO;
import com.example.shopee.Exception.ResourceNotFoundException;
import com.example.shopee.Model.Address;
import com.example.shopee.Model.User;
import com.example.shopee.Repo.AddressRepo;
import com.example.shopee.Repo.UserRepo;
import com.example.shopee.Service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;

    @Override
    public ResponseEntity<AddressResponseDTO> addAddress(AddressDto dto, String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Address> existingAddresses = addressRepo.findByUser(user);
        existingAddresses.forEach(addr -> addr.setDefault(false));
        addressRepo.saveAll(existingAddresses);

        Address address = new Address();
        address.setFullName(dto.getName());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setAddress(dto.getAddress());
        address.setLandmark(dto.getLandmark());
        address.setPinCode(dto.getPincode());
        address.setPhoneNumber(dto.getPhone());
        address.setDefault(true);
        address.setUser(user);

        Address saved = addressRepo.save(address);
        return ResponseEntity.ok(new AddressResponseDTO(saved));
    }

    @Override
    public ResponseEntity<List<AddressResponseDTO>> getUserAddresses(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<AddressResponseDTO> list = addressRepo.findByUser(user).stream()
                .map(AddressResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<List<AddressResponseDTO>> deleteAddress(Long id, String username) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        addressRepo.delete(address);
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<AddressResponseDTO> list = addressRepo.findByUser(user).stream()
                .map(AddressResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @Override
    public List<AddressResponseDTO> setDefaultAddress(Long id, String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Address> addresses = addressRepo.findByUser(user);

        for (Address address : addresses) {
            address.setDefault(address.getId().equals(id));
        }

        addressRepo.saveAll(addresses);

        return addresses.stream()
                .map(AddressResponseDTO::new)
                .collect(Collectors.toList());
    }

}
