package com.dev.pilates.services;

import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.entities.Student;
import com.dev.pilates.repositories.StudentRepository;
import com.dev.pilates.services.student.StudentServices;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.awaitility.Awaitility.given;
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
        dateTime = LocalDateTime.now();

        student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setIs_active(true);
        student.setClassesList(new ArrayList<>());
        student.setCreated_at(dateTime);
        student.setUpdated_at(dateTime);

        studentRepository.deleteAll();
        studentRepository.save(student);
    }

    @Test
    @Order(1)
    void testFindAllStudents() {
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
    @Order(2)
    void testFindStudentById() {
        assertNotNull(student.getId());

        StudentResponseDTO retrievedStudent = studentServices.findStudentById(student.getId());
        assertNotNull(retrievedStudent);

        assertEquals(student.getId(), retrievedStudent.id());
        assertEquals(student.getFirstName(), retrievedStudent.firstName());
        assertEquals(student.getLastName(), retrievedStudent.lastName());
        assertEquals(student.getIs_active(), retrievedStudent.is_active());
    }
}
