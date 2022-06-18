package com.codecool.ordersystem.service;

import com.codecool.ordersystem.dto.CustomerDTO;
import com.codecool.ordersystem.entity.Customer;
import com.codecool.ordersystem.repository.CustomerRepository;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
//        return customerRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Customer with id %d not found", id)));
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(new Customer(null,
                customerDTO.getName(),
                customerDTO.getZipCode(),
                customerDTO.getCity(),
                customerDTO.getAddress()));
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = findById(id).orElseThrow();
        customer.setName(customerDTO.getName());
        customer.setZipCode(customerDTO.getZipCode());
        customer.setCity(customerDTO.getCity());
        customer.setAddress(customerDTO.getAddress());
        return customerRepository.save(customer);
    }


    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
