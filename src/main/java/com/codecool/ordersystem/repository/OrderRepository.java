package com.codecool.ordersystem.repository;

import com.codecool.ordersystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.seller.id = ?1")
    List<Order> findBySellerId(Long id);

    @Query("select o from Order o where o.buyer.id = ?1")
    List<Order> findByBuyerId(Long id);

    @Query("select o from Order o where o.shipped = false")
    List<Order> findOrdersToShip();

    @Query("select o from Order o join o.orders i where i.product.id = ?1")
    List<Order> findByProductId(Long id);

    @Query("select o from Order o join o.orders oi where o.buyer.id = ?1  AND o.shipped = true")
    List<Order> findBuyerSumById(Long id);

    @Query("select o from Order o join o.orders oi where o.shipped = true")
    List<Order> findBuyerSum();

}
