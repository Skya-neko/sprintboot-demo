package com.violet.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.violet.demo.model.entity.Contact;
import org.springframework.data.repository.query.Param;

public class StudentRequest {
    private String name;

    @JsonProperty("dept_id")
    private Long departmentId;
    private Contact contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
