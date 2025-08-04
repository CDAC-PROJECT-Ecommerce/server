package com.example.shopee.Repo.UserOrder;

import com.example.shopee.Model.User;
import com.example.shopee.Model.UserOrder.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
