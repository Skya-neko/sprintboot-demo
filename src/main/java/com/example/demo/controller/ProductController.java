package com.example.demo.controller;

import com.example.demo.exception.APIResponseException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.param.ProductRequestParameter;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private static ResponseEntity<ProductResVO> getProductResVOResponseEntity(APIResponseException e, List<ProductVO> productVOList) {
        ResponseEntity<ProductResVO> result;
        System.out.println(e.getMessage());
        ProductResVO req = new ProductResVO();
        req.setStatusCode(e.getStatusCode());
        req.setMessage(e.getMessage());
        req.addProducts(productVOList);
        result = ResponseEntity.ok(req);
        return result;
    }

    private static ResponseEntity<ProductResVO> getProductResVOResponseEntity(APIResponseException e, ProductVO productVO) {
        ResponseEntity<ProductResVO> result;
        System.out.println(e.getMessage());
        ProductResVO req = new ProductResVO();
        req.setStatusCode(e.getStatusCode());
        req.setMessage(e.getMessage());
        req.addProduct(productVO);
        result = ResponseEntity.ok(req);
        return result;
    }

    private static ResponseEntity<ProductResVO> getProductResVOResponseEntity(APIResponseException e) {
        ResponseEntity<ProductResVO> result;
        System.out.println(e.getMessage());
        ProductResVO req = new ProductResVO();
        req.setStatusCode(e.getStatusCode());
        req.setMessage(e.getMessage());
        result = ResponseEntity.ok(req);
        return result;
    }

    private static ResponseEntity<ProductResVO> getProductResVOResponseEntity(Exception e) {
        ResponseEntity<ProductResVO> result;
        System.out.println(e.getMessage());
        ProductResVO req = new ProductResVO();
        req.setStatusCode(400);
        req.setMessage(e.getMessage());
        result = ResponseEntity.ok(req);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResVO> getProduct(@PathVariable("id") String productId) {
        ResponseEntity<ProductResVO> result = null;
        System.out.println("=========== Start ProductController.getProduct ===========");
        try {
            ProductVO product = productService.getProductVO(productId);

            ProductResVO req = new ProductResVO();
            req.setStatusCode(200);
            req.setMessage("Success");
            req.addProduct(product);
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
    public ResponseEntity<ProductResVO> createProduct(@RequestBody ProductCreateRequest request) {
        ResponseEntity<ProductResVO> result = null;
        System.out.println("=========== Start ProductController.createProduct ===========");
        try {
            ProductPO product = productService.createProduct(request);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .build(Map.of("id", product.getId()));

            ProductVO productVO = ProductVO.of(product);
            ProductResVO req = new ProductResVO();
            req.setStatusCode(201);
            req.setMessage("Success");
            req.addProduct(productVO);
            result = ResponseEntity.created(uri).body(req);

        } catch (APIResponseException e) {
            result = getProductResVOResponseEntity(e);
        } catch (Exception e) {
            result = getProductResVOResponseEntity(e);
        } finally {
            System.out.println("=========== Start ProductController.createProduct ===========");

        }
        return result;

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResVO> updateProduct(
            @PathVariable("id") String productId,
            @RequestBody ProductUpdateRequest request) {
        ResponseEntity<ProductResVO> result = null;
        System.out.println("=========== Start ProductController.updateProduct ===========");
        try {
            productService.updateProduct(productId, request);

        } catch (APIResponseException e) {
            result = getProductResVOResponseEntity(e);
        } catch (Exception e) {
            result = getProductResVOResponseEntity(e);
        } finally {
            System.out.println("=========== Start ProductController.updateProduct ===========");

        }
        return result;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResVO> deleteProduct(@PathVariable("id") String productId) {
        ResponseEntity<ProductResVO> result = null;
        System.out.println("=========== Start ProductController.updateProduct ===========");
        try {
            productService.deleteProduct(productId);
        } catch (APIResponseException e) {
            result = getProductResVOResponseEntity(e);
        } catch (Exception e) {
            result = getProductResVOResponseEntity(e);
        } finally {
            System.out.println("=========== End ProductController.updateProduct ===========");

        }
        return result;
    }

    @GetMapping("")
    public ResponseEntity<ProductResVO> getProducts(
            @ModelAttribute ProductRequestParameter param
    ) {
        ResponseEntity<ProductResVO> result = null;
        System.out.println("=========== Start ProductController.getProducts ===========");
        try {
            List<ProductVO> products = productService.getProductVOs(param);

            ProductResVO req = new ProductResVO();
            req.setStatusCode(200);
            req.setMessage("Success");
            req.getList().addAll(products);
            result = ResponseEntity.ok(req);
        } catch (Exception e) {
            ProductResVO req = new ProductResVO();
            req.setStatusCode(400);
            req.setMessage("Error: " + e.getMessage());
            result = ResponseEntity.ok(req);

        } finally {
            System.out.println("=========== End ProductController.getProducts ===========");

        }
        return result;

    }
}
