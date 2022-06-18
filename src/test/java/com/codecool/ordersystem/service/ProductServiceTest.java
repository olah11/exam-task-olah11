package com.codecool.ordersystem.service;

import com.codecool.ordersystem.dto.ProductDTO;
import com.codecool.ordersystem.entity.Product;
import com.codecool.ordersystem.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    ProductRepository mockRepository;

    @Autowired
    ProductService productService;



    @Test
    void testFindAllProduct() {
        List<Product> productExpected = Arrays.asList(
                new Product(1L, "Product1","1234",1000),
                new Product(2L, "Product2","221234",2000 ),
                new Product(3L, "Product3","33331234",3000 )
        );
        Mockito.when(mockRepository.findAll()).thenReturn(productExpected);
        List<Product> customerLst = productService.findAll();
        assertTrue(customerLst.size() == productExpected.size() && customerLst.containsAll(productExpected)
                && productExpected.containsAll(customerLst));
    }

    @Test
    void testFindProductById() {
        Product product = new Product(1L, "Product1","1234",1000);
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(product));
        assertEquals(product, productService.findById(1L));
    }

    @Test
    void testAddingProduct() {
        ProductDTO productDTO = new ProductDTO("Product1","1234",1000);
        Product product = new Product(null,
                productDTO.getName(),
                productDTO.getEanCode(),
                productDTO.getPrice());
        Mockito.when(mockRepository.save(product)).thenReturn(product);
        assertEquals(product, productService.saveProduct(productDTO));
        productService.saveProduct(productDTO);
        Mockito.verify(mockRepository, times(2)).save(product);
    }

    @Test
    void testDeletingProductById() {
        productService.deleteById(1L);
        Mockito.verify(mockRepository).deleteById(1L);
    }


}