package com.violet.demo.controller;

import com.violet.demo.model.Certificate;
import com.violet.demo.model.Contact;
import com.violet.demo.model.Student;
import com.violet.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Range;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MyController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/students")
    public ResponseEntity<Void> createStudent(@RequestBody Student student) {
        System.out.println("================== Start MyController.createStudent ==================");
        try {
            student.setId(null);
            studentRepository.insert(student);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .build(Map.of("id", student.getId()));
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("================== End MyController.createStudent ==================");
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/students/reset")
    public ResponseEntity<Void> resetStudents() {
        Student s1 = new Student();
        s1.setName("Vincent");
        s1.setGrade(2);
        s1.setBirthday(LocalDate.of(1996, 1, 1));
        s1.setContact(Contact.of("vincent@school.com", "0911111111"));
        s1.setCertificates(List.of(
                Certificate.of("GEPT", null, "Medium"),
                Certificate.of("TOEIC", 990, "Gold")
        ));

        Student s2 = new Student();
        s2.setName("Dora");
        s2.setGrade(3);
        s2.setBirthday(LocalDate.of(1995, 1, 1));
        s2.setContact(Contact.of("dora@school.com", "0922222222"));
        s2.setCertificates(List.of(
                Certificate.of("TOEFL", 85, null),
                Certificate.of("TOEIC", 900, "Gold")
        ));

        Student s3 = new Student();
        s3.setName("Ivy");
        s3.setGrade(4);
        s3.setBirthday(LocalDate.of(1994, 1, 1));
        s3.setContact(Contact.of("ivy@school.com", "0933333333"));
        s3.setCertificates(List.of(
                Certificate.of("IELTS", 5, null)
        ));

        studentRepository.deleteAll();
        studentRepository.insert(List.of(s1, s2, s3));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable String id) {
        System.out.println("================== MyController.getStudent ==================");
        try {
            Optional<Student> studentOp = studentRepository.findById(id);
            return studentOp.isPresent()
                    ? ResponseEntity.ok(studentOp.get())
                    : ResponseEntity.notFound().build();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("================== MyController.getStudent ==================");

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("students/name/{name}")
    public ResponseEntity<Student> getStudentByName(@PathVariable String name) {
        try {
            System.out.println("================== Start MyController.getStudentByName ==================");

            Student s = studentRepository.findByName(name);
            return ResponseEntity.ok(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("================== End MyController.getStudentByName ==================");

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("students/ContactEmail/{email}")
    public ResponseEntity<Student> getByContactEmail(@PathVariable String email) {
        try {
            System.out.println("================== Start MyController.getByContactEmail ==================");

            Student s = studentRepository.findByContactEmail(email);
            return ResponseEntity.ok(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("================== End MyController.getByContactEmail ==================");

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("students/ids")
    public ResponseEntity<List<Student>> getStudents(@RequestParam("idList") List<String> idList) {
        List<Student> students = studentRepository.findAllById(idList);
        return ResponseEntity.ok(students);

    }

    @GetMapping("students/CertificatesType/{type}")
    public ResponseEntity<List<Student>> getStudentsByCertificatesType(@PathVariable String type) {
        try {
            System.out.println("================== Start MyController.getStudentsByCertificatesType ==================");

            List<Student> students = studentRepository.findByCertificatesType(type);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("================== End MyController.getStudentsByCertificatesType ==================");

        }
        return ResponseEntity.notFound().build();

    }


    @GetMapping("students/findByGrade")
    public ResponseEntity<List<Student>> getByGrade(
            @Param("from") int from
            , @Param("to") int to
    ) {
        try {
            System.out.println("================== Start MyController.getByGrade ==================");
            Range<Integer> range = Range.of(
                    Range.Bound.inclusive(from),
                    Range.Bound.inclusive(to)
            );
            List<Student> students = studentRepository.findByGradeBetween(range);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("================== End MyController.getByGrade ==================");

        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("students/GradeAndBirthday")
    public ResponseEntity<List<Student>> getStudentsByGradeAndBirthday(
            @Param("gradeFrom") int gradeFrom
            , @Param("gradeTo") int gradeTo

            , @RequestParam("birthdayFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dFrom
            , @RequestParam("birthdayTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dTo
    ) {
        try {
            System.out.println("================== Start MyController.getStudentsByGradeAndBirthday ==================");
            Range<Integer> gradeRange = Range.of(
                    Range.Bound.inclusive(gradeFrom),
                    Range.Bound.inclusive(gradeTo)
            );
            System.out.println(gradeTo);
            System.out.println(dFrom);
            System.out.println(dTo);
            List<Student> students = studentRepository.findByGradeBetweenAndBirthdayBetween(gradeRange, dFrom, dTo);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("================== End MyController.getStudentsByGradeAndBirthday ==================");

        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("students/getStudentsByContact")
    public ResponseEntity<List<Student>> getStudentsByContact(
            @RequestParam(value = "email", required = false) String email
            , @RequestParam("phone") String phone
    ) {
        try {
            System.out.println("================== Start MyController.getStudentsByContact ==================");
            List<Student> students = studentRepository.findByContact(email, phone);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("================== End MyController.getStudentsByContact ==================");

        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("students/{id}")
    public ResponseEntity<Void> updateStudent(@PathVariable String id, @RequestBody Student request) {
        Optional<Student> studentOp = studentRepository.findById(id);
        if (studentOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOp.get();
        student.setName(request.getName());
        student.setGrade(request.getGrade());
        student.setBirthday(request.getBirthday());
        student.setContact(request.getContact());
        student.setCertificates(request.getCertificates());
        studentRepository.save(student);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
