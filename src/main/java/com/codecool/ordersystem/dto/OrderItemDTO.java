package com.codecool.ordersystem.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class OrderItemDTO {
    private Long id;
    private int quantity;
    @Min(value = 0, message = "Price should not be less than 0")
    private double sellingPrice;
    @Min(value = 0, message = "ProductId should be bigger than 0")
    private Long productId;
}
