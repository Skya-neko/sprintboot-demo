package com.violet.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;


@RestController
public class MailController {

    @PostMapping("/mail")
    public ResponseEntity<Void> sendPlainText(@RequestBody SendMailRequest request) {
        JavaMailSenderImpl sender = createMailSender();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(String.format("%s<%s>", "Spring Mail", sender.getUsername()));
        msg.setTo(request.getReceivers());
        msg.setSubject(request.getSubject());
        msg.setText(request.getContent());
        sender.send(msg);


        return ResponseEntity.noContent().build();
    }

    private JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        //郵件服務主機
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);

        //郵件服務帳密
        sender.setUsername("violetviolet1022@gmail.com");
        sender.setPassword("jwab mcal vkxy tsnh");

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", true); //是否向郵件服務驗證身份
        props.put("mail.smtp.starttls.enable", true); //是否啟用 TLS(傳輸層安全)，對通訊加密
        props.put("mail.transport.protocol", "smtp"); //傳輸協定

        return sender;


    }
}