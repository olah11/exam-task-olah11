package com.codecool.ordersystem.service;

import com.codecool.ordersystem.dto.CustomerDTO;
import com.codecool.ordersystem.entity.Customer;
import com.codecool.ordersystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

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
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(new Customer(null,
                customerDTO.getName(),
                customerDTO.getZipCode(),
                customerDTO.getCity(),
                customerDTO.getAddress()));
    }

    public Optional<Customer> updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> customer = findById(id);
        if (customer.isEmpty()) {
            return customer;
        }
        customer.get().setName(customerDTO.getName());
        customer.get().setZipCode(customerDTO.getZipCode());
        customer.get().setCity(customerDTO.getCity());
        customer.get().setAddress(customerDTO.getAddress());
        return Optional.of(customerRepository.save(customer.get()));
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
