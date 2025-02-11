package com.dev.pilates.services;

import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.entities.Student;
import com.dev.pilates.repositories.StudentRepository;
import com.dev.pilates.services.student.StudentServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStudentService {


    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServices studentServices;

    private List<Student> students;

    @BeforeEach
    void setUp() {
        students = List.of(
                createStudent(1L,"John", "Doe", true),
                createStudent(2L,"Jane", "Silver", false)
        );

    }

    @Test
    @Order(1)
    void testSaveStudent() {
    }

    @Test
    @Order(2)
    void testFindAllStudents() {
        when(studentRepository.findAll()).thenReturn(students);
        List<StudentResponseDTO> results = studentServices.findAll();

        assertNotNull(results);
        assertEquals(students.size(), results.size());

        for (int i = 0; i < students.size(); i++) {
            assertEquals(students.get(i).getFirstName(), results.get(i).firstName());
            assertEquals(students.get(i).getLastName(), results.get(i).lastName());
            assertEquals(students.get(i).getIs_active(), results.get(i).is_active());
        }
    }

    @Test
    @Order(3)
    void testFindStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(students.get(0)));

        StudentResponseDTO retrievedStudent = studentServices.findStudentById(1L);

        assertNotNull(retrievedStudent);
        assertEquals(students.get(0).getFirstName(), retrievedStudent.firstName());
        assertEquals(students.get(0).getLastName(), retrievedStudent.lastName());
        assertEquals(students.get(0).getIs_active(), retrievedStudent.is_active());
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

    private Student createStudent(Long id, String firstName, String lastName, boolean isActive) {
        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setIs_active(isActive);
        student.setClassesList(new ArrayList<>());
        student.setCreated_at(LocalDateTime.now());
        student.setUpdated_at(LocalDateTime.now());
        return student;
    }
}
