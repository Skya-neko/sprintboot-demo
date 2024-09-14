package com.violet.demo.repository;

import com.violet.demo.entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM `student`"
    )
    List<Student> find(Pageable pageable);
}
