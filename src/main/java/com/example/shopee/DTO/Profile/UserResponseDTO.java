package com.example.shopee.DTO.Profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponseDTO {
    private String fullName;
    private String email;

}
