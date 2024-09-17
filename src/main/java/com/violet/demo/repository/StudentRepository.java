package com.violet.demo.repository;

import com.violet.demo.model.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @EntityGraph(attributePaths = "contact")
    List<Student> findByNameLikeIgnoreCase(String name);
}
