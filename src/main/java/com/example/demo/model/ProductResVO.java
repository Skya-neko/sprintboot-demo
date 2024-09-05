package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class ProductResVO {
    private int statusCode;
    private String message;
    private List<ProductVO> list = new ArrayList<>();

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductVO> getList() {
        return list;
    }

    public void addProduct
            (ProductVO productVO) {
        this.list.add(productVO);
    }

    public void addProducts(List<ProductVO> productVOList) {
        this.list.addAll(productVOList);
    }
}
