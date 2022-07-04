package com.codecool.ordersystem;

import com.codecool.ordersystem.entity.Product;
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
public class ProductTestApplication {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private String url = "/product";

    Product[] expProductArr = {
            new Product(1L, "Product1", "1234", 1000),
            new Product(2L, "Product2", "221234", 2000),
            new Product(3L, "Product3", "33331234", 3000),
            new Product(4L, "Product4", "43331444", 4000)
    };

    @Test
    void testAddProduct() {
        Product product = new Product(null, "Product5", "55555555", 5000);
        postProductToUrl(product, url);
        Product[] newProductArr = new Product[expProductArr.length + 1];
        for (int i = 0; i < expProductArr.length; i++) {
            newProductArr[i] = expProductArr[i];
        }
        product.setId(5L);
        newProductArr[newProductArr.length - 1] = product;
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(url, Product[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(newProductArr, responseEntity.getBody());
        testRestTemplate.delete(url + "/" + 5L);
    }

    @Test
    void testFindAll() {
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(url, Product[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArraysHasSameElements(expProductArr, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        ResponseEntity<Product> responseEntity = testRestTemplate.getForEntity(url + "/2", Product.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Product productAct = responseEntity.getBody();
        assertEquals(expProductArr[1], productAct);
    }

    @Test
    void testDeleteById() {
        testRestTemplate.delete(url + "/" + expProductArr[expProductArr.length - 1].getId());
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(url, Product[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Product[] deletedArray = Arrays.copyOfRange(expProductArr, 0, expProductArr.length - 1);
        assertArraysHasSameElements(deletedArray, responseEntity.getBody());
    }

    private void assertArraysHasSameElements(Product[] expectedArr, Product[] actualArr) {
        List<Product> expected = Arrays.asList(expectedArr);
        List<Product> actual = Arrays.asList(actualArr);
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual)
                && actual.containsAll(expected));
    }

    private void postProductToUrl(Product product, String url) {
        HttpEntity<Product> httpEntity = createHttpEntity(product);
        testRestTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<Product> createHttpEntity(Product product) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(product, httpHeaders);
    }

}
