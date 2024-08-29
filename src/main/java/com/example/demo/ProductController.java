package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Map<String, Product> productMap = new HashMap<>();

    static {
        Stream.of(
                Product.of("B1", "Android Development (Java)", 380),
                Product.of("B2", "Android Development (Kotlin)", 420),
                Product.of("B3", "Data Structure (Java)", 250),
                Product.of("B4", "Finance Management", 450),
                Product.of("B5", "Human Resource Management", 330)
        ).forEach(p -> productMap.put(p.getId(), p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String productId) {
        var product = productMap.get(productId);
        return product == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(product);
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(
            @ModelAttribute ProductRequestParameter param
    ) {
        var sortField = param.getSortField();
        var sortDir = param.getSortDir();
        var keyword = param.getSearchKey();
        //準備排序欄位
        Comparator<Product> comparator;
        if ("name".equals(sortField)) {
            comparator = Comparator.comparing(x -> x.getName().toLowerCase());
        } else if ("price".equals(sortField)) {
            comparator = Comparator.comparing(Product::getPrice);
        } else {
            comparator = (a, b) -> 0;
        }

        //判斷是否遞減
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }


        //執行
        List<Product> products = productMap.values()
                .stream()
                .filter(x -> x.getName().toLowerCase().contains(keyword.toLowerCase()) || x.getId().contains(keyword))
                .sorted(comparator)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(products);

    }

    @PostMapping("")
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (productMap.containsKey(product.getId())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        productMap.put(product.getId(), product);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .build(Map.of("id", product.getId()));


        return ResponseEntity.created(uri).build();

        /*method 2
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();

        * */
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable("id") String productId,
            @RequestBody Product request) {
        var product = productMap.get(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("id") String productId) {
        if (!productMap.containsKey(productId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productMap.remove(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
