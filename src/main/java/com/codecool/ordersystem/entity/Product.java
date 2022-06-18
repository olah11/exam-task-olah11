package com.codecool.ordersystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    @NotBlank(message = "Product name is mandatory")
    private String name;

    @Column(length = 13)
    @Size(min = 0, max = 13, message = "Must be between 0 and 13 characters")
    private String eanCode;

    @Min(value = 0, message = "Price should not be less than 0")
    private double price;
}
