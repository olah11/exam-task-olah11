package com.codecool.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private int quantity;
    @Min(value = 0, message = "Price should not be less than 0")
    private double sellingPrice;
    @NotNull(message = "Product cannot be null")
    @Min(value = 1, message = "ProductId should be bigger than 0")
    private Long productId;
}
