package com.codecool.ordersystem.controller;

import com.codecool.ordersystem.dto.ProductDTO;
import com.codecool.ordersystem.entity.Product;
import com.codecool.ordersystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Get list of products")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by id", description = "Get a product by id  - required: productId  ")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productOpt = productService.findById(id);
        if (productOpt.isEmpty()) {
            logger.error("Not found: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found! " + id);
        } else return ResponseEntity.ok(productOpt.get());
    }

    @PostMapping
    @Operation(summary = "Save a new product", description = "Save a new product - required: productDTO")
    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductDTO productDTO,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Save error! Invalid product data! " +
                    Arrays.toString(bindingResult.getFieldErrors().stream().toArray()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product data error! " +
                    bindingResult.getFieldError().getField() + ":  " +
                    bindingResult.getFieldError().getDefaultMessage().toString());
        }
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Update a product - required: productId, productDTO")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Update error! Invalid product data! " +
                    Arrays.toString(bindingResult.getFieldErrors().stream().toArray()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product data error! " +
                    bindingResult.getFieldError().getField() + ":  " +
                    bindingResult.getFieldError().getDefaultMessage().toString());
        }
        Optional<Product> productOpt = productService.updateProduct(id, productDTO);
        if (productOpt.isEmpty()) {
            logger.error("Update error! Product not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found! " + id);
        }
        return ResponseEntity.ok(productOpt.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product - required: productId")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.ok("Product succesfully deleted! " + id);
        } catch (Exception e) {
            logger.error("Delete error!! " + id + "  " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete error! " + id);
        }
    }
}
