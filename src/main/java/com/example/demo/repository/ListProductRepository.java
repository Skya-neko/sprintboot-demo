package com.example.demo.repository;

import com.example.demo.model.ProductPO;
import com.example.demo.param.ProductRequestParameter;

import java.util.List;


public class ListProductRepository implements IProductRepository {
    @Override
    public ProductPO getOneById(String id) {
        return null;
    }

    @Override
    public ProductPO insert(ProductPO product) {
        return null;
    }

    @Override
    public void update(ProductPO product) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public List<ProductPO> getMany(ProductRequestParameter param) {
        return List.of();
    }
}
