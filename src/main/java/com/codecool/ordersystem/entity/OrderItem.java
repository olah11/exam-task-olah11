package com.codecool.ordersystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orderitems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @Column(name = "selling_price")
    @Min(value = 0, message = "Price should not be less than 0")
    private double sellingPrice;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "Product cannot be null")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
}
