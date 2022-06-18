package com.codecool.ordersystem.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private LocalDate date = LocalDate.now();
    private Boolean shipped = false;
    private LocalDate shippingDate;
    @Min(value = 0, message = "SellerId should be bigger than 0")
    private Long sellerId;
    @Min(value = 0, message = "BuyerId should be bigger than 0")
    private Long buyerId;
    private List<OrderItemDTO> orders;
}
