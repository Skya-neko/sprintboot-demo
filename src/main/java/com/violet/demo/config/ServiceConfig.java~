package com.violet.demo.config;

import com.violet.demo.repository.ProductRepository;
import com.violet.demo.service.MailService;
import com.violet.demo.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public ProductService productService(
            ProductRepository repository
            , MailService mailService
    ) {
        System.out.println("Product Service is created.");
        return new ProductService(repository, mailService);
    }
}
