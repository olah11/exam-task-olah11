package com.codecool.ordersystem.repository;

import com.codecool.ordersystem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepositpry extends JpaRepository<OrderItem, Long> {
}
