package com.codecool.ordersystem.controller;

import com.codecool.ordersystem.dto.CustomerDTO;
import com.codecool.ordersystem.entity.Customer;
import com.codecool.ordersystem.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Get list of customerss")
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a customer by id", description = "Get a customer by id  - required: customerId  ")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Customer> customerOpt = customerService.findById(id);
        if (customerOpt.isEmpty()) {
            logger.error("Not found: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found! " + id);
        } else return ResponseEntity.ok(customerOpt.get());
    }

    @PostMapping
    @Operation(summary = "Save a new customer", description = "Save a new customerr - required: customerDTO")
    public ResponseEntity<?> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Save error! Invalid customer data! " +
                    Arrays.toString(bindingResult.getFieldErrors().stream().toArray()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer data error! " +
                    bindingResult.getFieldError().getField() + ":  " +
                    bindingResult.getFieldError().getDefaultMessage().toString());
        }
        return ResponseEntity.ok(customerService.saveCustomer(customerDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer", description = "Update a customer - required: customerId, customerDTO")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,
                                            @RequestBody @Valid CustomerDTO customerDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Save error! Invalid customer data! " + Arrays.toString(bindingResult.getFieldErrors().stream().toArray()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer data error! " +
                    bindingResult.getFieldError().getField() + ":  " +
                    bindingResult.getFieldError().getDefaultMessage().toString());
        }
        if (customerService.updateCustomer(id, customerDTO).isEmpty()) {
            logger.error("Update erro! Customer not found!  " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found! " + id);
        }
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO).get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer", description = "Delete a customer - required: customerId")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            customerService.deleteById(id);
            return ResponseEntity.ok("Customer succesfully deleted! " + id);
        } catch (Exception e) {
            logger.error("Delete error!! " + id + "  " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete error! " + id);
        }
    }
}
