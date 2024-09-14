package com.violet.demo.entity;

import com.violet.demo.entity.base.BaseContact;
import jakarta.persistence.Embeddable;

@Embeddable
public class Contact extends BaseContact {
    public static Contact of(String email, String phone) {
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);
        return contact;
    }
}
