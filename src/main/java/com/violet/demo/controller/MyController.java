package com.violet.demo.controller;

import com.violet.demo.model.entity.Contact;
import com.violet.demo.model.entity.Student;
import com.violet.demo.model.response.StudentResponse;
import com.violet.demo.repository.ContactRepository;
import com.violet.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class MyController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getStudents(
            @RequestParam(required = false, defaultValue = "") String name
    ) {
        System.out.println("============= Start MyController.getStudents =============");
        try {
            List<Student> students = studentRepository.findByNameLikeIgnoreCase("%" + name + "%");
            Map<Student, Contact> studentContactMap = createStudentContactMap(students);

            List<StudentResponse> responses = students
                    .stream()
                    .map(s -> {
                        Contact contact = studentContactMap.get(s);
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

    private Map<Student, Contact> createStudentContactMap(List<Student> students) {
        Map<Student, Long> studentLongMap = students.stream().collect(Collectors.toMap(s -> s, student -> student.getContact().getId()));
        List<Contact> contacts = contactRepository.findAllById(studentLongMap.values());
        Map<Long, Contact> contactMap = contacts.stream().collect(Collectors.toMap(contact -> contact.getId(), contact -> contact));

        Map<Student, Contact> studentContactMap = new HashMap<>();
        students.forEach(student -> {
            studentContactMap.put(student, contactMap.get(studentLongMap.get(student)));
        });

        return studentContactMap;
    }

    @GetMapping("students/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable("id") Long id) {
        System.out.println("============= Start MyController.getStudent =============");
        try {
            Optional<Student> student = studentRepository.findById(id);


            return ResponseEntity.ok(student);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============= End MyController.getStudent =============");

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("students/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
        System.out.println("============= Start MyController.getStudent =============");
        try {
            studentRepository.deleteById(id);

            return ResponseEntity.ok("student id: " + id + ", has been removed.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============= End MyController.getStudent =============");

        }
        return ResponseEntity.notFound().build();
    }


}