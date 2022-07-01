package com.codecool.ordersystem;

import com.codecool.ordersystem.dto.*;
import com.codecool.ordersystem.entity.Customer;
import com.codecool.ordersystem.entity.OrderItem;
import com.codecool.ordersystem.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderTestApplication {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String url = "/orders";

    private List<OrderItem> orderItems = new ArrayList<>();
    OrderResponseDTO[] expOrderArr = {
            new OrderResponseDTO(1L, LocalDate.parse("2022-05-21"), true, LocalDate.parse("2022-05-30"),
                    new Customer(1L, "Seller1", "1234", "Budapest", "Eladó u.1."),
                    new Customer(3L, "Buyer1", "B-1425", "Hatvan", "Buyer u.1."),
                    Arrays.asList(
                            new OrderItemResponseDTO(1L,1,1500.0D,
                                    new Product(1L, "Product1","1234",1000))
                    )
            ),
            new OrderResponseDTO(2L, LocalDate.parse("2022-06-21"), false, null,
                    new Customer(1L, "Seller1", "1234", "Budapest", "Eladó u.1."),
                    new Customer(4L, "Buyer2", "B-5127", "Miskolc", "Buyer u.2."),
                    Arrays.asList(
                            new OrderItemResponseDTO(2L,2,2000.0D,
                                    new Product(2L, "Product2","221234",2000)),
                            new OrderItemResponseDTO(3L,3,2200.0D,
                                    new Product(3L, "Product3","33331234",3000))
                    )
            ),
            new OrderResponseDTO(3L, LocalDate.parse("2022-07-01"), false,  null,
                    new Customer(2L, "Seller2", "2222", "Budapest", "Eladó u.2."),
                    new Customer(3L, "Buyer1", "B-1425", "Hatvan", "Buyer u.1."),
                    Arrays.asList(
                            new OrderItemResponseDTO(4L,3,3000.0D,
                                    new Product(2L, "Product2","221234",2000))
                    )
            )
    };

    @Test
    void testAddCustomer() {
        OrderDTO order = new OrderDTO(
                LocalDate.parse("2022-05-21"), false, null,1L,3L,
                Arrays.asList(
                        new OrderItemDTO(1L,1,1500.0D,1L)
        ));
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                4L, LocalDate.parse("2022-05-21"), false, null,
                new Customer(1L, "Seller1", "1234", "Budapest", "Eladó u.1."),
                new Customer(3L, "Buyer1", "B-1425", "Hatvan", "Buyer u.1."),
                Arrays.asList(
                        new OrderItemResponseDTO(5L,1,1500.0D,
                                new Product(1L, "Product1","1234",1000))
                )
        );
        postOrderToUrl(order, url);
        OrderResponseDTO[] newOrderArr = new OrderResponseDTO[expOrderArr.length + 1];
        for (int i = 0; i < expOrderArr.length; i++) {
            newOrderArr[i] = expOrderArr[i];
        }
        newOrderArr[newOrderArr.length - 1] = orderResponseDTO;
        ResponseEntity<OrderResponseDTO[]> responseEntity = testRestTemplate.getForEntity(url, OrderResponseDTO[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(newOrderArr, responseEntity.getBody());
       testRestTemplate.delete(url + "/" + 4L);
    }

    @Test
    void testFindAll() {
        ResponseEntity<OrderResponseDTO[]> responseEntity = testRestTemplate.getForEntity(url, OrderResponseDTO[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(expOrderArr, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        ResponseEntity<OrderResponseDTO> responseEntity = testRestTemplate.getForEntity(url + "/2", OrderResponseDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        OrderResponseDTO orderAct = responseEntity.getBody();
        assertEquals(expOrderArr[1], orderAct);
    }


    @Test
    void testFindProduct() {
        ResponseEntity<OrderResponseDTO[]> responseEntity = testRestTemplate.getForEntity(url+"/1/product", OrderResponseDTO[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        OrderResponseDTO[] expOrderProductArr =  new OrderResponseDTO[] { expOrderArr[0] };
        assertArraysHasSameElements(expOrderProductArr, responseEntity.getBody());
    }

    @Test
    void testOrderToShip() {
        ResponseEntity<OrderResponseDTO[]> responseEntity = testRestTemplate.getForEntity(url+"/toship", OrderResponseDTO[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        OrderResponseDTO[] expOrderProductArr =  new OrderResponseDTO[] {expOrderArr[1],expOrderArr[2]};
        assertArraysHasSameElements(expOrderProductArr, responseEntity.getBody());
    }

    @Test
    void testOrderByBuyer() {
        ResponseEntity<OrderResponseDTO[]> responseEntity = testRestTemplate.getForEntity(url+"/3/buyer", OrderResponseDTO[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        OrderResponseDTO[] expOrderProductArr =  new OrderResponseDTO[] {expOrderArr[0],expOrderArr[2]};
        assertArraysHasSameElements(expOrderProductArr, responseEntity.getBody());
    }

    @Test
    void testBuyerSum() {
        OrderSumDTO[] expected = {new OrderSumDTO(3L,"Buyer1",1500D)};
        ResponseEntity<OrderSumDTO[]> responseEntity = testRestTemplate.getForEntity(url + "/buyersum", OrderSumDTO[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSumArraysHasSameElements(expected, responseEntity.getBody());
    }

    @Test
    void testDeleteById() {
        testRestTemplate.delete(url + "/" + expOrderArr[expOrderArr.length - 1].getId());
        ResponseEntity<OrderResponseDTO[]> responseEntity = testRestTemplate.getForEntity(url, OrderResponseDTO[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        OrderResponseDTO[] deletedArray = Arrays.copyOfRange(expOrderArr, 0, expOrderArr.length - 1);
        assertArraysHasSameElements(deletedArray, responseEntity.getBody());
    }

    private void assertArraysHasSameElements(OrderResponseDTO[] expectedArr, OrderResponseDTO[] actualArr) {
        List<OrderResponseDTO> expected = Arrays.asList(expectedArr);
        List<OrderResponseDTO> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual)
                && actual.containsAll(expected));
    }

    private void assertSumArraysHasSameElements(OrderSumDTO[] expectedArr, OrderSumDTO[] actualArr) {
        List<OrderSumDTO> expected = Arrays.asList(expectedArr);
        List<OrderSumDTO> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual)
                && actual.containsAll(expected));
    }

    private void postOrderToUrl(OrderDTO order, String url) {
        HttpEntity<OrderDTO> httpEntity = createHttpEntity(order);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<OrderDTO> createHttpEntity(OrderDTO order) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(order, httpHeaders);
    }
}
