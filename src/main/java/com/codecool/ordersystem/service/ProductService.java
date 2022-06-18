package com.codecool.ordersystem.service;

import com.codecool.ordersystem.dto.ProductDTO;
import com.codecool.ordersystem.entity.Product;
import com.codecool.ordersystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, ProductDTO productDTO) {
        Product  product = findById(id);

        product.setName(productDTO.getName());
        product.setEanCode(productDTO.getEanCode());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);
    }

    public Product saveProduct(ProductDTO productDTO) {
        return productRepository.save(new Product(
                null,
                productDTO.getName(),
                productDTO.getEanCode(),
                productDTO.getPrice()
        ));
    }
}
