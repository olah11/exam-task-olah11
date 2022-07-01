package com.codecool.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Product name is mandatory")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String name;
    @Size(min = 0, max = 13, message = "Must be between 0 and 13 characters")
    private String eanCode;
    @Min(value = 0, message = "Price should not be less than 0")
    private double price;
}
