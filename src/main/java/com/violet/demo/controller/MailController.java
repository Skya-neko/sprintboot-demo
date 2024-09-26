package com.violet.demo.controller;

import com.violet.demo.config.MailConfig;
import com.violet.demo.entity.SendMailRequest;
import com.violet.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;


@RestController
@RequestMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_VALUE)
public class MailController {

    @Autowired
    private MailService mailService;


    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody SendMailRequest request) {
        System.out.println("============= Start MailController.sendPlainText =============");
        try {
            mailService.sendMail(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============= End MailController.sendPlainText =============");
        }
        return ResponseEntity.noContent().build();
    }


}