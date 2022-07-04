package com.codecool.ordersystem.dto;

import com.codecool.ordersystem.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private LocalDate date;
    private Boolean shipped;
    private LocalDate shippingDate;
    private Customer seller;
    private Customer buyer;
    private List<OrderItemResponseDTO> orders;
}
