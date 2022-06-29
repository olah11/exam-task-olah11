package com.codecool.ordersystem.controller;

import com.codecool.ordersystem.dto.OrderDTO;
import com.codecool.ordersystem.dto.OrderResponseDTO;
import com.codecool.ordersystem.service.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(title = "Order System",version = "1.0",description = "Ensure data for Order System")
)
@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Get list of orders")
    public List<OrderResponseDTO> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by id", description = "Get an order by id  - required: orderId  ")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<OrderResponseDTO> order = orderService.findById(id);
        if (order.isEmpty()) {
            logger.error("Not found: " +id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found! " + id);
        } else return ResponseEntity.ok(order);
    }

    @GetMapping("/{sellerId}/seller")
    @Operation(summary = "Get orders by seller", description = "Get list of orders by seller - required: sellerId")
    public List<OrderResponseDTO> findBySellerId(@PathVariable(name = "sellerId") Long id) {
        return orderService.findBySellerId(id);
    }

    @GetMapping("/{productId}/product")
    @Operation(summary = "Get orders by product", description =
            "Get list of orders contains product - required: productId ")
    public List<OrderResponseDTO> findByProductId(@PathVariable(name = "productId") Long id) {
        return orderService.findByProductId(id);
    }

    @GetMapping("/toship")
    @Operation(summary = "Get orders to ship", description = "Get list of orders to ship")
    public List<OrderResponseDTO> findOrdersToShip() {
        return orderService.findOrdersToShip();
    }

    @GetMapping("/{buyerId}/buyer")
    @Operation(summary = "Get orders by buyer", description = "Get list of orders - required: buyerId")
    public List<OrderResponseDTO> findByBuyerId(@PathVariable(name = "buyerId") Long id) {
        return orderService.findByBuyerId(id);
    }

    @PostMapping
    @Operation(summary = "Save a new order", description = "Save a new order - required: orderDTO")
    public ResponseEntity<?> saveOrder(@RequestBody @Valid  OrderDTO orderDTO,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Save error! Invalid customer data!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order data error! ");
        }
        return ResponseEntity.ok(orderService.saveOrder(orderDTO));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update an order", description = "Update an order - required: orderId, orderDTO")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody  @Valid OrderDTO orderDTO,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Update error! Invalid order data!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order data error! " + id);
        }
        Optional<OrderResponseDTO> order = orderService.updateOrder(id, orderDTO);
        if (order.isEmpty()) {
            logger.error("Update error! Order data error!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order data error! " + id);
        }
        return ResponseEntity.ok(order.get());
    }

    @PutMapping("/{orderId}/shipping")
    @Operation(summary = "Set order to shipped", description = "Set an order to shipped - required: orderId")
    public ResponseEntity<?> shippingOrder(@PathVariable(name = "orderId") Long id) {
        Optional<OrderResponseDTO> order = orderService.shippingOrder(id);
        if (order.isEmpty()) {
            logger.error("Update error! Order not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found! " + id);
        }
        return ResponseEntity.ok(order.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Delete an order - required: orderId")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            orderService.deleteById(id);
            return ResponseEntity.ok("Order succesfully deleted! " +id);
        } catch (Exception e) {
            logger.error("Delete error!! " +id + "  " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order delete error! " + id);
        }
    }
}
