package com.example.shopee.Repo.UserOrder;

import com.example.shopee.Model.User;
import com.example.shopee.Model.UserOrder.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Long> {
    List<Orders> findByUser(User user);
}
