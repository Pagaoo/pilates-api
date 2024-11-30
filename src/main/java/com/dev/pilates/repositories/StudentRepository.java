package com.dev.pilates.repositories;

import com.dev.pilates.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT studentName FROM Student studentName WHERE studentName.firstName = :firstName")
    List<Student> findStudentByFirstName(@Param("firstName") String firstName);
}
