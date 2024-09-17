package com.violet.demo.controller;

import com.violet.demo.model.entity.Contact;
import com.violet.demo.model.entity.Student;
import com.violet.demo.model.response.StudentResponse;
import com.violet.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getStudents(
            @RequestParam(required = false, defaultValue = "") String name
    ) {

        System.out.println("============= Start MyController.getStudents =============");
        try {
            List<Student> students = studentRepository.findByNameLikeIgnoreCase("%" + name + "%");
            List<StudentResponse> responses = students
                    .stream()
                    .map(s -> {
                        Contact contact = s.getContact();
                        StudentResponse res = new StudentResponse();
                        res.setId(s.getId());
                        res.setName(s.getName());
                        res.setEmail(contact.getEmail());
                        res.setPhone(contact.getPhone());
                        return res;
                    })
                    .toList();

            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============= End MyController.getStudents =============");

        }
        return ResponseEntity.notFound().build();

    }
}
