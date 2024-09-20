package com.violet.demo.controller;

import com.violet.demo.entity.Product;
import com.violet.demo.entity.ProductRequest;
import com.violet.demo.parameter.ProductQueryParameter;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import com.violet.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Environment environment;

    @GetMapping("/checkUserName")
    public ResponseEntity<String> checkUserName() {
        System.out.println("============= Start ProductController.getProducts =============");
        try {
            String userName = environment.getProperty("USERNAME");
            System.out.println(userName);
            return ResponseEntity.ok(userName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============= End ProductController.getProducts =============");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@ModelAttribute ProductQueryParameter param) {
        System.out.println("============= Start ProductController.getProducts =============");
        try {
            String userName = environment.getProperty("USERNAME");
            System.out.println(userName);
            List<Product> products = productService.getProducts(param);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============= End ProductController.getProducts =============");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/post")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        System.out.println("============= Start ProductController.createProduct =============");
        try {
            Product product = productService.createProduct(request);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getId())
                    .toUri();

            return ResponseEntity.created(location).body(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============= End ProductController.createProduct =============");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(
            @PathVariable("id") String id, @RequestBody @Valid Product request) {
        Product product = productService.replaceProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
