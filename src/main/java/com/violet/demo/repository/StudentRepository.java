package com.violet.demo.repository;

import com.violet.demo.model.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameLikeIgnoreCase(String name);

    @EntityGraph(attributePaths = {"contact"})
    Optional<Student> findById(Long id);
}
