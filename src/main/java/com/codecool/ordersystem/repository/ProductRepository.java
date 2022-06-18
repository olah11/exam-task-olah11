package com.codecool.ordersystem.repository;

import com.codecool.ordersystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
