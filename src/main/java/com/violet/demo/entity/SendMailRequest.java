package com.violet.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class SendMailRequest {
    private List<String> receivers = new ArrayList<>();
    private String subject = "";
    private String content = "";

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}