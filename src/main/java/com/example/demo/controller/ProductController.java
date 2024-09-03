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
import java.util.Objects;

@RestController
@RequestMapping("")
public class ProductController {

    private static final ProductService productService = new ProductService();


    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResVo> getProduct(@PathVariable("id") String productId) {
        System.out.println("=========== Start ProductController.getProduct ============");

        ResponseEntity<ProductResVo> result = null;
        try {
            var product = productService.getProductVO(productId);


            ProductResVo req = new ProductResVo();
            req.setCode(200);
            req.setMessage("Success");
            result = ResponseEntity.ok(req);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ProductResVo req = new ProductResVo();
            req.setCode(404);
            req.setMessage(e.getMessage());
            result = ResponseEntity.ok(req);
        } finally {
            System.out.println("=========== End ProductController.getProduct ============");
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


    @GetMapping("/products")
    public ResponseEntity<List<ProductVO>> getProducts(
            @ModelAttribute ProductRequestParameter param
    ) {
        System.out.println("=========== Start ProductController.getProducts ============");

        try {
            var products = productService.getProductVOs(param);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            System.out.println("=========== End ProductController.getProducts ============");
        }

        return null;

    }
}
