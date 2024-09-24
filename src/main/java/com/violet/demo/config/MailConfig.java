package com.violet.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
    @Value("${mail.display-name}")
    private String displayName;

    @Value("${spring.mail.username}")
    private String userName;

    public String getDisplayName() {
        return displayName;
    }

    public String getUserName() {
        return userName;
    }
}
