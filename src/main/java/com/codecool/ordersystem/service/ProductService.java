package com.codecool.ordersystem.service;

import com.codecool.ordersystem.dto.ProductDTO;
import com.codecool.ordersystem.entity.Product;
import com.codecool.ordersystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product>  product = findById(id);
        if (product.isPresent()) {
            product.get().setName(productDTO.getName());
            product.get().setEanCode(productDTO.getEanCode());
            product.get().setPrice(productDTO.getPrice());
        } else return product;
        return Optional.of(productRepository.save(product.get()));
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
