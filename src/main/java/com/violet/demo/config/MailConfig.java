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
    private Boolean gmailSmtpAuth;

    @Value("${spring.mail.gmail.properties.mail.smtp.starttls.enable}")
    private Boolean gmailSmtpTlsEnable;

    @Value("${spring.mail.gmail.properties.mail.transport.protocol}")
    private String gmailTransProtocol;

    public String getDisplayName() {
        return displayName;
    }

    public String getUserName() {
        return userName;
    }

    @Bean("mailService")
    public MailService mailServiceByPlatform() {
        switch (platform) {
            case "gmail":
                return googleMailService();
            case "yahoo":
                return yahooMailService();
            default:
                return null;
        }
    }


    public MailService googleMailService() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(gmailHost);
        sender.setPort(gmailPort);
        sender.setUsername(gmailUsername);
        sender.setPassword(gmailPassword);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", gmailSmtpAuth); //是否向郵件服務驗證身份
        props.put("mail.smtp.starttls.enable", gmailSmtpTlsEnable); //是否啟用 TLS(傳輸層安全)，對通訊加密
        props.put("mail.transport.protocol", gmailTransProtocol); //傳輸協定

        return new MailService(sender);
    }

    public MailService yahooMailService() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        return new MailService(sender);
    }
}
