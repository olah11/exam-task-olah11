package com.codecool.ordersystem.controller;

import com.codecool.ordersystem.dto.CustomerDTO;
import com.codecool.ordersystem.entity.Customer;
import com.codecool.ordersystem.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Customer customer = customerService.findById(id).orElse(null);
        if (customer == null) {
            logger.error("Invalid id! " + id);
            return ResponseEntity.badRequest().build();
        } else return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody CustomerDTO customerDTO,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Invalid customer!");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(customerService.saveCustomer(customerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@Valid @PathVariable Long id,
                                                   @RequestBody CustomerDTO customerDTO,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            customerService.deleteById(id);
            ResponseEntity.ok("Customer deleted!");
            return ResponseEntity.ok("Customer deleted!");
        } catch (Exception e) {
            logger.error("Delete error!! " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
