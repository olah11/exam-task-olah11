package com.codecool.ordersystem;

import com.codecool.ordersystem.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerTestApplication {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String url = "/customer";

    Customer[] expCustomerArr = {
            new Customer(1L, "Seller1", "1234", "Budapest", "Eladó u.1."),
            new Customer(2L, "Seller2", "2222", "Budapest", "Eladó u.2."),
            new Customer(3L, "Buyer1", "B-1425", "Hatvan", "Buyer u.1."),
            new Customer(4L, "Buyer2", "B-5127", "Miskolc", "Buyer u.2."),
            new Customer(5L, "Buyer3", "33333", "Szeged", "Buyer u.3.")
    };

    @Test
    void testAddCustomer() {
        Customer customer = new Customer(null, "SellerTeszt", "1234x", "Budapest", "Eladó u.5.");
        postCustomerToUrl(customer, url);
        Customer[] newCustomerArr = new Customer[expCustomerArr.length + 1];
        for (int i = 0; i < expCustomerArr.length; i++) {
            newCustomerArr[i] = expCustomerArr[i];
        }
        customer.setId(6L);
        newCustomerArr[newCustomerArr.length - 1] = customer;
        ResponseEntity<Customer[]> responseEntity = testRestTemplate.getForEntity(url, Customer[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(newCustomerArr, responseEntity.getBody());
        testRestTemplate.delete(url + "/" + 6L);
    }

    @Test
    void testFindAll() {
        ResponseEntity<Customer[]> responseEntity = testRestTemplate.getForEntity(url, Customer[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(expCustomerArr, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        ResponseEntity<Customer> responseEntity = testRestTemplate.getForEntity(url + "/2", Customer.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Customer customerAct = responseEntity.getBody();
        assertEquals(expCustomerArr[1], customerAct);
    }

    @Test
    void testDeleteById() {
        testRestTemplate.delete(url + "/" + expCustomerArr[expCustomerArr.length - 1].getId());
        ResponseEntity<Customer[]> responseEntity = testRestTemplate.getForEntity(url, Customer[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Customer[] deletedArray = Arrays.copyOfRange(expCustomerArr, 0, expCustomerArr.length - 1);
        assertArraysHasSameElements(deletedArray, responseEntity.getBody());
    }

    private void assertArraysHasSameElements(Customer[] expectedArr, Customer[] actualArr) {
        List<Customer> expected = Arrays.asList(expectedArr);
        List<Customer> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual)
                && actual.containsAll(expected));
    }

    private void postCustomerToUrl(Customer customer, String url) {
        HttpEntity<Customer> httpEntity = createHttpEntity(customer);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<Customer> createHttpEntity(Customer customer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(customer, httpHeaders);
    }

}
