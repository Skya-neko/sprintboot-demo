package com.violet.demo.service;

import com.violet.demo.config.MailConfig;
import com.violet.demo.entity.SendMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MailService {

    //    @Qualifier("GmailSender")
    public JavaMailSenderImpl gmailSender;
    @Autowired
    public MailConfig mailConfig;

    public MailService(JavaMailSenderImpl gmailSender) {
        this.gmailSender = gmailSender;
    }

    public ResponseEntity<Void> sendPlainText(@RequestBody SendMailRequest request) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(String.format("%s<%s>", mailConfig.getDisplayName(), mailConfig.getUserName()));
        msg.setTo(request.getReceivers());
        msg.setSubject(request.getSubject());
        msg.setText(request.getContent());
        gmailSender.send(msg);


        return ResponseEntity.noContent().build();
    }
}
