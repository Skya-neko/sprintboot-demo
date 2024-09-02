package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.param.ProductRequestParameter;
import com.example.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final ProductService productService = new ProductService();


    @GetMapping("/{id}")
    public ResponseEntity<ProductVO> getProduct(@PathVariable("id") String productId) {
        var product = productService.getProductVO(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping("")
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequest request) {
        var product = productService.createProduct(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .build(Map.of("id", product.getId()));


        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable("id") String productId,
            @RequestBody ProductUpdateRequest request) {
        productService.updateProduct(productId, request);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("")
    public ResponseEntity<List<ProductVO>> getProducts(
            @ModelAttribute ProductRequestParameter param
    ) {
        var products = productService.getProductVOs(param);
        return ResponseEntity.ok(products);

    }
}
