package com.codecool.ordersystem.controller;

import com.codecool.ordersystem.dto.OrderDTO;
import com.codecool.ordersystem.entity.Order;
import com.codecool.ordersystem.service.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@OpenAPIDefinition(
        info = @Info(title = "Order System",version = "1.0",description = "Ensure data for Order System")
)
@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Get list of orders")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by id", description = "Get an order by id  - required: orderId  ")
//    @Parameter( name = "id",schema = @Schema(type = "Long",description = "order id"))
    public Order findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/{sellerId}/seller")
    @Operation(summary = "Get orders by seller", description = "Get list of orders by seller - required: sellerId")
    public List<Order> findBySellerId(@PathVariable(name = "sellerId") Long id) {
        return orderService.findBySellerId(id);
    }

    @GetMapping("/{productId}/product")
    @Operation(summary = "Get orders by product", description =
            "Get list of orders contains product - required: productId ")
    public List<Order> findByProductId(@PathVariable(name = "productId") Long id) {
        return orderService.findByProductId(id);
    }

    @GetMapping("/toship")
    @Operation(summary = "Get orders to ship", description = "Get list of orders to ship")
    public List<Order> findOrdersToShip() {
        return orderService.findOrdersToShip();
    }

    @GetMapping("/{buyerId}/buyer")
    @Operation(summary = "Get orders by buyer", description = "Get list of orders - required: buyerId")
    public List<Order> findByBuyerId(@PathVariable(name = "buyerId") Long id) {
        return orderService.findByBuyerId(id);
    }

    @PostMapping
    @Operation(summary = "Save a new order", description = "Save a new order - required: orderDTO")
    public Order saveOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.saveOrder(orderDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an order", description = "Update an order - required: orderId, orderDTO")
    public void updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        orderService.updateCustomer(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Delete an order - required: orderId")
    public void deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
