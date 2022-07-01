package com.codecool.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {
    private LocalDate date = LocalDate.now();
    private Boolean shipped = false;
    private LocalDate shippingDate;
    @NotNull(message = "Seller cannot be null")
    @Min(value = 1, message = "SellerId should be bigger than 0")
    private Long sellerId;
    @NotNull(message = "Buyer cannot be null")
    @Min(value = 1, message = "BuyerId should be bigger than 0")
    private Long buyerId;
    private List<OrderItemDTO> orders;
}
