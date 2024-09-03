package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.param.ProductRequestParameter;
import com.example.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final ProductService productService = new ProductService();


    @GetMapping("/{id}")
    public ResponseEntity<ProductResVO> getProduct(@PathVariable("id") String productId) {
        ResponseEntity<ProductResVO> result = null;
        System.out.println("=========== Start ProductController.getProduct ===========");
        try {
            ProductVO product = productService.getProductVO(productId);

            ProductResVO req = new ProductResVO();
            req.setStatusCode(200);
            req.setMessage("Success");
            req.addList(product);
            result = ResponseEntity.ok(req);

        } catch (Exception e) {
            ProductResVO req = new ProductResVO();
            req.setStatusCode(404);
            req.setMessage(e.getMessage());
            result = ResponseEntity.ok(req);
        } finally {
            System.out.println("=========== End ProductController.getProduct ===========");

        }
        return result;
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
