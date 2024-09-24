package com.violet.demo.converter;

import com.violet.demo.entity.Product;
import com.violet.demo.entity.ProductRequest;

public class ProductConverter {
    private ProductConverter() {

    }

    public static Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        return product;
    }
}