package com.violet.demo.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {
    @NotEmpty(message = "Product name is undefined.")
    private String name;

    @NotNull
    @Min(value = 0, message = "Price should be greater than or equal to 0.")
    private int price;

    public @NotEmpty(message = "Product name is undefined.") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Product name is undefined.") String name) {
        this.name = name;
    }

    @NotNull
    @Min(value = 0, message = "Price should be greater than or equal to 0.")
    public int getPrice() {
        return price;
    }

    public void setPrice(@NotNull @Min(value = 0, message = "Price should be greater than or equal to 0.") int price) {
        this.price = price;
    }
}