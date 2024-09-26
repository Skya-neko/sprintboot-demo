package com.violet.demo.config;

import com.violet.demo.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {
    @Value("${mail.platform}")
    private String platform;

    @Value("${mail.display-name}")
    private String displayName;

    @Value("${spring.mail.gmail.username}")
    private String userName;

    @Value("${spring.mail.gmail.host}")
    private String gmailHost;

    @Value("${spring.mail.gmail.port}")
    private int gmailPort;

    @Value("${spring.mail.gmail.username}")
    private String gmailUsername;

    @Value("${spring.mail.gmail.password}")
    private String gmailPassword;

    @Value("${spring.mail.gmail.properties.mail.smtp.auth}")
    private Boolean authEnabled;

    @Value("${spring.mail.gmail.properties.mail.smtp.starttls.enable}")
    private Boolean starttlsEnabled;

    @Value("${spring.mail.gmail.properties.mail.transport.protocol}")
    private String protocol;

    public String getDisplayName() {
        return displayName;
    }

    public String getUserName() {
        return userName;
    }

    @Bean
    public MailService mailService() {
        JavaMailSenderImpl mailSender = "gmail".equals(platform)
                ? gmailSender()
                : yahooSender();

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", authEnabled);
        props.put("mail.smtp.starttls.enable", starttlsEnabled);
        props.put("mail.transport.protocol", protocol);

        System.out.println("Mail Service is created.");
        return new MailService(mailSender);
    }

    private JavaMailSenderImpl gmailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(gmailHost);
        mailSender.setPort(gmailPort);
        mailSender.setUsername(gmailUsername);
        mailSender.setPassword(gmailPassword);

        return mailSender;
    }

    private JavaMailSenderImpl yahooSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        return mailSender;
    }
}
