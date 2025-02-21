package com.dev.pilates.services;

import com.dev.pilates.dtos.student.StudentRequestDTO;
import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.entities.Student;
import com.dev.pilates.exceptions.CreatingEntityException;
import com.dev.pilates.repositories.StudentRepository;
import com.dev.pilates.services.student.StudentServices;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.converter.HttpMessageConversionException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
                createStudent(2L,"Jane", "Silver", false),
                createStudent(3L, "John", "Teste", true)
        );
    }

    @Test
    @Order(1)
    void testSaveStudent() {
        StudentRequestDTO studentRequestDTO = students.get(0).toStudentRequestDTO();

        when(studentRepository.save(any(Student.class))).thenReturn(students.get(0));

        StudentRequestDTO savedStudent = studentServices.save(studentRequestDTO);

        assertNotNull(savedStudent);
        assertEquals("John", savedStudent.firstName());
        assertEquals("Doe", savedStudent.lastName());
        assertTrue(savedStudent.is_active());

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @Order(2)
    void shouldThrowHttpMessageConversionException_whenMissingFieldsOrConversionFails() {
        StudentRequestDTO studentRequestDTO = mock(StudentRequestDTO.class);

        when(studentRequestDTO.toStudent()).thenThrow(new HttpMessageConversionException("Erro de conversão"));

        HttpMessageConversionException exception =
                assertThrows(HttpMessageConversionException.class, () -> studentServices.save(studentRequestDTO));

        assertEquals("Erro de conversão", exception.getMessage());
    }

    @Test
    @Order(3)
    void shouldThrowCreatingEntityException_whenSaveFails() {
        StudentRequestDTO studentRequestDTO = students.get(0).toStudentRequestDTO();

        when(studentRepository.save(any(Student.class))).thenThrow(new RuntimeException("Erro ao criar aluno"));

        CreatingEntityException exception =
                assertThrows(CreatingEntityException.class, () -> studentServices.save(studentRequestDTO));

        assertEquals("Erro ao criar aluno", exception.getMessage());
    }

    @Test
    @Order(4)
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
    @Order(5)
    void shouldThrowEntityNotFoundException_whenStudentsNotFound() {
        when(studentRepository.findAll()).thenThrow(new RuntimeException("Alunos não encontrados"));

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> studentServices.findAll());

        assertEquals("Alunos não encontrados", exception.getMessage());
    }

    @Test
    @Order(6)
    void testFindStudentById() {
        long idToFind = 1L;

        when(studentRepository.findById(idToFind)).thenReturn(Optional.of(students.get(0)));

        StudentResponseDTO retrievedStudent = studentServices.findStudentById(idToFind);

        assertNotNull(retrievedStudent);
        assertEquals(students.get(0).getFirstName(), retrievedStudent.firstName());
        assertEquals(students.get(0).getLastName(), retrievedStudent.lastName());
        assertEquals(students.get(0).getIs_active(), retrievedStudent.is_active());
    }

    @Test
    @Order(7)
    void shouldThrowEntityNotFoundException_whenStudentNotFoundById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> studentServices.findStudentById(1L));

        assertEquals("Aluno não encontrado", exception.getMessage());
    }

    @Test
    @Order(8)
    void testFindStudentByFirstName() {
        String nameToFilter = "John";
        when(studentRepository.findAll(any(Specification.class)))
                .thenReturn(students.stream()
                .filter(student -> student.getFirstName().equals(nameToFilter))
                .collect(Collectors.toList()));

        long listSize = students.stream().filter(student -> student.getFirstName().equals(nameToFilter)).count();

        List<StudentResponseDTO> retrievedStudents = studentServices.findStudentsByName(nameToFilter);

        assertNotNull(retrievedStudents);
        assertEquals(listSize, retrievedStudents.size());

        for (StudentResponseDTO student : retrievedStudents) {
            assertEquals(nameToFilter, student.firstName());
        }
    }

    @Test
    @Order(9)
    void testUpdateStudent() {
        long studentIdToUpdate = 1L;
        StudentRequestDTO studentRequestDTO = students.get((int) studentIdToUpdate).toStudentRequestDTO();

        StudentRequestDTO studentToUpdate = new StudentRequestDTO("John", "troquei", true);

        when(studentRepository.findById(studentIdToUpdate)).thenReturn(Optional.ofNullable(students.getFirst()));
        when(studentRepository.save(any(Student.class))).thenReturn(studentToUpdate.toStudent());

        StudentRequestDTO updatedStudent = studentServices.updateStudentById(studentIdToUpdate, studentRequestDTO);

        assertNotNull(updatedStudent);
        assertEquals(studentToUpdate.firstName(), updatedStudent.firstName());
        assertEquals(studentToUpdate.lastName(), updatedStudent.lastName());
        assertTrue(updatedStudent.is_active());

        //é o save pq no final do metodo de update eu salvo o student
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @Order(10)
    void testDeleteStudent() {
        long idToDelete = 1L;
        //não passando o when pois o metodo delete é void

        studentRepository.deleteById(idToDelete);
        verify(studentRepository).deleteById(idToDelete);
    }

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
