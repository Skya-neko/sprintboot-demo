package com.violet.demo.controller;

import com.violet.demo.config.MailConfig;
import com.violet.demo.entity.SendMailRequest;
import com.violet.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;


@RestController
public class MailController {

    @Autowired
    private MailService mailService;


    @PostMapping("/mail")
    public ResponseEntity<Void> sendEmail(@RequestBody SendMailRequest request) {
        System.out.println("============= Start MailController.sendPlainText =============");
        try {
            mailService.sendPlainText(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============= End MailController.sendPlainText =============");
        }
        return ResponseEntity.noContent().build();
    }


}