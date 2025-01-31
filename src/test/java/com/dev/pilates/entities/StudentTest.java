package com.dev.pilates.entities;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class StudentTest {

    @Autowired
    private EntityManager em;

    private Student student;
    private LocalDateTime dateTime;

    @BeforeEach
    public void setUp() {
        dateTime = LocalDateTime.now();

        student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setIs_active(true);
        student.setClassesList(new ArrayList<>());
        student.setCreated_at(dateTime);
        student.setUpdated_at(dateTime);
        em.persist(student);

        em.flush();
        em.clear();
    }

    @Test
    public void testFindAndRetrieveStudent() {
        Student retrievedStudent = em.find(Student.class, student.getId());

        assertNotNull(retrievedStudent);
        assertNotNull(retrievedStudent.getId());
        assertEquals("John", retrievedStudent.getFirstName());
        assertEquals("Doe", retrievedStudent.getLastName());
        assertTrue(retrievedStudent.getIs_active());
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedStudent.getCreated_at().truncatedTo(ChronoUnit.MILLIS));
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedStudent.getUpdated_at().truncatedTo(ChronoUnit.MILLIS));
    }
}
