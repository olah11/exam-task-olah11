package com.codecool.ordersystem.service;

import com.codecool.ordersystem.dto.CustomerDTO;
import com.codecool.ordersystem.entity.Customer;
import com.codecool.ordersystem.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class CustomerServiceTest {
    @MockBean
    CustomerRepository mockRepository;
    @Autowired
    CustomerService customerService;

    @Test
    void testFindAllCustomer() {
        List<Customer> customerExpected = Arrays.asList(
                new Customer(1L, "Seller1", "1234", "Budapest", "Eladó u.1."),
                new Customer(2L, "Seller2", "2222", "Budapest", "Eladó u.2."),
                new Customer(3L, "Buyer1", "B-1425", "Hatvan", "Buyer u.1."),
                new Customer(4L, "Buyer2", "B-5127", "Miskolc", "Buyer u.2."),
                new Customer(5L, "Buyer3", "33333", "Szeged", "Buyer u.3.")
        );

        Mockito.when(mockRepository.findAll()).thenReturn(customerExpected);
        List<Customer> customerLst = customerService.findAll();
        assertTrue(customerLst.size() == customerExpected.size() && customerLst.containsAll(customerExpected)
                && customerExpected.containsAll(customerLst));
    }

    @Test
    void testFindCustomerById() {
        Customer customer = new Customer(1L, "Seller1", "1234", "Budapest", "Eladó u.1.");
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(customer));
        assertEquals(customer, customerService.findById(1L).get());
    }

    @Test
    void testAddingCustmer() {
        CustomerDTO customerDTO = new CustomerDTO("SellerTeszt", "1234x", "Budapest", "Eladó u.5.");
        Customer customer = new Customer(null,
                customerDTO.getName(),
                customerDTO.getZipCode(),
                customerDTO.getCity(),
                customerDTO.getAddress());
        Mockito.when(mockRepository.save(customer)).thenReturn(customer);
        assertEquals(customer, customerService.saveCustomer(customerDTO));
        customerService.saveCustomer(customerDTO);
        Mockito.verify(mockRepository, times(2)).save(customer);
    }

    @Test
    void testDeletingCustomerById() {
        customerService.deleteById(1L);
        Mockito.verify(mockRepository).deleteById(1L);
    }
}