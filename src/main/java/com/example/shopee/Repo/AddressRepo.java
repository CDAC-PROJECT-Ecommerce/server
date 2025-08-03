package com.example.shopee.Repo;

import com.example.shopee.Model.Address;
import com.example.shopee.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address,Long> {
    List<Address> findByUser(User user);
}
