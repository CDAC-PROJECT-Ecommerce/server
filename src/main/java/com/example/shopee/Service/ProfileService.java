package com.example.shopee.Service;

import java.util.List;

import com.example.shopee.Model.User;

public interface ProfileService {
    User getProfileById(Long id);
    void saveProfile(User profile);
    void deleteProfile(Long id);
}
