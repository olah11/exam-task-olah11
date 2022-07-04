package com.codecool.ordersystem.dto;

import com.codecool.ordersystem.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private int quantity;
    private double sellingPrice;
    private Product product;
}
