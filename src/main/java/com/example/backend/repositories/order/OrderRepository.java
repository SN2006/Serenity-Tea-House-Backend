package com.example.backend.repositories.order;

import com.example.backend.entity.User;
import com.example.backend.entity.order.Order;
import com.example.backend.entity.order.ProductInOrder;
import com.example.backend.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUser(User user);
    Integer countByUser(User user);
}
