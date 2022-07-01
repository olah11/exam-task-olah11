package com.codecool.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSumDTO {
    private Long id;
    private String name;
    private double sum;
}
