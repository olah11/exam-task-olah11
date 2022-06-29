package com.codecool.ordersystem.repository;

import com.codecool.ordersystem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepositpry extends JpaRepository<OrderItem, Long> {

    @Query("DELETE FROM OrderItem o WHERE o.order is null")
    void deleteOrderItemByid(Long id);
}
