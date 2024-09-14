package com.violet.demo.controller;

import com.violet.demo.entity.BloodType;
import com.violet.demo.entity.Contact;
import com.violet.demo.entity.Student;
import com.violet.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MyController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/students/initTestData")
    public ResponseEntity<List<Student>> initTestData() {
        try {
            System.out.println("============== Start MyController.initTestData ==============");

            Student s1 = new Student();
            s1.setName("Vincent");
            s1.setGrade(2);
            s1.setBloodType(BloodType.B);
            s1.setBirthday(LocalDate.of(1996, 1, 1));
            s1.setContact(Contact.of("vincent@school.com", "0911111111"));

            Student s2 = new Student();
            s2.setName("Dora");
            s2.setGrade(3);
            s2.setBloodType(BloodType.A);
            s2.setBirthday(LocalDate.of(1995, 1, 1));
            s2.setContact(Contact.of("dora@school.com", "0922222222"));

            Student s3 = new Student();
            s3.setName("Ivy");
            s3.setGrade(4);
            s3.setBloodType(BloodType.AB);
            s3.setBirthday(LocalDate.of(1994, 1, 1));
            s3.setContact(Contact.of("ivy@school.com", "0933333333"));

            List<Student> students = List.of(s1, s2, s3);
            studentRepository.saveAll(students);

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============== End MyController.initTestData ==============");
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
            System.out.println("============== Start MyController.createStudent ==============");
            student.setId(null);
            studentRepository.save(student);

            return ResponseEntity.ok(student);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============== End MyController.createStudent ==============");

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents(
            @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , @RequestParam(value = "sortField", required = false) String sortField
            , @RequestParam(value = "sortDirect", required = false) String sortDirect

    ) {
        try {
            System.out.println("============== Start MyController.getStudents ==============");
            Sort sort = createSort(sortField, sortDirect);
            Pageable pageable = createPageable(page, size, sort);
            List<Student> students = studentRepository.find(pageable);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("============== End MyController.getStudents ==============");

        }
        return ResponseEntity.notFound().build();
    }

    private Sort createSort(String field, String direction) {
        if (field == null || direction == null) {
            return Sort.unsorted();
        }
        if (direction.equalsIgnoreCase("asc")) {
            return Sort.by(Sort.Order.asc(field));
        } else if (direction.equalsIgnoreCase("desc")) {
            return Sort.by(Sort.Order.desc(field));
        } else {
            return Sort.unsorted();
        }
    }

    private Pageable createPageable(Integer page, Integer size, Sort sort) {
        if (page == null || size == null) {
            return Pageable.unpaged(sort);
        }
        if (page < 0 || size <= 0) {
            return Pageable.unpaged(sort);
        }

        return PageRequest.of(page, size, sort);

    }


}
