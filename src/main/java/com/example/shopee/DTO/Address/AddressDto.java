package com.example.shopee.DTO.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressDto {
    @NotBlank(message = "Full name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Address is required")
    private String address;

    private String landmark;

    @NotBlank(message = "PinCode is required")
    private String pincode;

    @NotBlank(message = "Country is required")
    private String country;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private boolean isDefault;

}
