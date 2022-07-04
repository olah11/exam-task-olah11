package com.codecool.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CustomerDTO {
    @NotBlank(message = "Customer name is mandatory")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String name;
    @NotNull(message = "Zipcode cannot be null")
    @Size(min = 2, max = 8, message = "Must be between 2 and 8 characters")
    private String zipCode;
    @NotNull(message = "City cannot be null")
    @Size(min = 2, max = 40, message = "Must be between 2 and 40 characters")
    private String city;
    @NotNull(message = "Address cannot be null")
    @Size(min = 2, max = 80, message = "Must be between 2 and 80 characters")
    private String address;
}
