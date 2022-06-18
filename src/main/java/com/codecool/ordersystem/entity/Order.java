package com.codecool.ordersystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date = LocalDate.now();
    private Boolean shipped = false;

    @Column(name = "shipping_date")
    @FutureOrPresent(message = "Shipping date should be present or future time!")
    private LocalDate shippingDate;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Customer seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Customer buyer;

    @OneToMany(mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    private List<OrderItem> orders;
}
