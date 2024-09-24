package com.violet.demo.config;

import com.violet.demo.repository.ProductRepository;
import com.violet.demo.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public ProductService productService(ProductRepository repository) {
        return new ProductService(repository);
    }
}
