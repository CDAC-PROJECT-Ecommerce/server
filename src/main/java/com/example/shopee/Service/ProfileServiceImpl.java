package com.example.shopee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopee.Model.User;
import com.example.shopee.Repo.UserRepo;

@Service
public class ProfileServiceImpl implements ProfileService{
	
	@Autowired
    private UserRepo repository;
	

	@Override
	public User getProfileById(Long id) {
        return repository.findById(id).orElse(null);
	}

	@Override
	public void saveProfile(User profile) {
        repository.save(profile);
	}

	@Override
	public void deleteProfile(Long id) {
        repository.deleteById(id);
	}

}
