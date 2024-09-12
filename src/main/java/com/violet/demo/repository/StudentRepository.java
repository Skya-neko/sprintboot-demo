package com.violet.demo.repository;

import com.violet.demo.model.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Student findByName(String s);

    Student findByContactEmail(String s);

    Student findByContactPhone(String s);

    List<Student> findByCertificatesType(String s);

    List<Student> findByGradeGreaterThanEqual(int from);

    List<Student> findByGradeLessThan(int to);

    List<Student> findByBirthdayAfter(LocalDate from);

    List<Student> findByBirthdayBefore(LocalDate to);

    List<Student> findByGradeBetween(Range<Integer> range);

    List<Student> findByBirthdayBetween(LocalDate from, LocalDate to);

    List<Student> findByGradeBetweenAndBirthdayBetween(Range<Integer> range, LocalDate from, LocalDate to);

    @Query("""
                {
                    "$or": [
                        { "contact.email": ?0 },
                        { "contact.phone": ?1 }
                    ]
                }
            """)
    List<Student> findByContact(String email, String phone);

    List<Student> findAllByOrderByGradeDescBirthdayAsc();

    @Query("{}")
    List<Student> findSort(Sort sort);

    @Query("{}")
    List<Student> findPage(Pageable pageable);


}
