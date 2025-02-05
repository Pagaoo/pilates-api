package com.dev.pilates.services;

import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.entities.Student;
import com.dev.pilates.repositories.StudentRepository;
import com.dev.pilates.services.student.StudentServices;
import com.dev.pilates.specifications.StudentSpecifications;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentServices studentServices;

    private Student student;
    private LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();

        dateTime = LocalDateTime.now();

        student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setIs_active(true);
        student.setClassesList(new ArrayList<>());
        student.setCreated_at(dateTime);
        student.setUpdated_at(dateTime);
    }

    @Test
    @Order(1)
    void testSaveStudent() {
        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent.getId());
        assertEquals(savedStudent.getFirstName(), student.getFirstName());
        assertEquals(savedStudent.getLastName(), student.getLastName());

        Optional<Student> foundStudent = studentRepository.findById(savedStudent.getId());
        assertTrue(foundStudent.isPresent());

    }

    @Test
    @Order(2)
    void testFindAllStudents() {
        student = studentRepository.save(student);
        List<StudentResponseDTO> retrievedStudent = studentServices.findAll();
        assertNotNull(retrievedStudent);
        assertEquals(1, retrievedStudent.size());
        assertFalse(retrievedStudent.isEmpty());

        StudentResponseDTO firstStudent = retrievedStudent.get(0);

        assertEquals(student.getId(), firstStudent.id());
        assertEquals(student.getFirstName(), firstStudent.firstName());
        assertEquals(student.getLastName(), firstStudent.lastName());
        assertEquals(student.getIs_active(), firstStudent.is_active());
    }

    @Test
    @Order(3)
    void testFindStudentById() {
        student = studentRepository.save(student);
        assertNotNull(student.getId());

        StudentResponseDTO retrievedStudent = studentServices.findStudentById(student.getId());
        assertNotNull(retrievedStudent);

        assertEquals(student.getId(), retrievedStudent.id());
        assertEquals(student.getFirstName(), retrievedStudent.firstName());
        assertEquals(student.getLastName(), retrievedStudent.lastName());
        assertEquals(student.getIs_active(), retrievedStudent.is_active());
    }

    @Test
    @Order(4)
    void testFindStudentByFirstName() {}

    @Test
    @Order(5)
    void testUpdateStudent() {}

    @Test
    @Order(6)
    void testDeleteStudent() {}
}
