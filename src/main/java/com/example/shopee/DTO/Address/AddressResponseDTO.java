package com.example.shopee.DTO.Address;

import com.example.shopee.Model.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponseDTO {

    private Long id;
    private String name;
    private String city;
    private String state;
    private String pincode;
    private String phone;
    private String address;
    private String landmark;
    private boolean isDefault;


    public AddressResponseDTO(Address address) {
        this.id = address.getId();
        this.name = address.getFullName();
        this.city = address.getCity();
        this.state = address.getState();
        this.pincode = address.getPinCode();
        this.phone = address.getPhoneNumber();
        this.address = address.getAddress();
        this.landmark= address.getLandmark();
        this.isDefault = address.isDefault();
    }
}
