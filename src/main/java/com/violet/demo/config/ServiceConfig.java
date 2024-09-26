package com.violet.demo.config;

import com.violet.demo.repository.ProductRepository;
import com.violet.demo.service.MailService;
import com.violet.demo.service.ProductService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class ServiceConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ProductService productService(
            ProductRepository repository
            , MailService mailService
    ) {
        System.out.println("Product Service is created.");
        return new ProductService(repository, mailService);
    }
}
