package com.violet.demo.model.entity;

import jakarta.persistence.*;
import org.springframework.data.repository.query.Param;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id", referencedColumnName = "id", nullable = false, unique = true)
    private Contact contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}