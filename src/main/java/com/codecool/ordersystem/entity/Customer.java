package com.codecool.ordersystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "Customer name is mandatory")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String name;

    @Column(name = "zip_code", length = 8)
    @NotNull(message = "Zipcode cannot be null")
    @Size(min = 0, max = 8, message = "Must be between 0 and 8 characters")
    private String zipCode;

    @Column(length = 40, nullable = false)
    @NotNull(message = "City cannot be null")
    @Size(min = 2, max = 40, message = "Must be between 1 and 40 characters")
    private String city;

    @Column(length = 80, nullable = false)
    @NotNull(message = "Address cannot be null")
    @Size(min = 0, max = 80, message = "Must be between 0 and 80 characters")
    private String address;
}
