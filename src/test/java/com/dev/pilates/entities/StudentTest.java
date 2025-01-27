package com.dev.pilates.entities;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class StudentTest {

    @Autowired
    private EntityManager em;

    @Test
    @Rollback(false)
    @Transactional
    public void testGettersAndSetters() {
        Student student = new Student();
        LocalDateTime dateTime;
        dateTime = LocalDateTime.now();

        student.setFirstName("John");
        student.setLastName("Doe");
        student.setIs_active(true);
        student.setCreated_at(dateTime);
        student.setUpdated_at(dateTime);

        student = em.merge(student);

        Student retrievedStudent = em.find(Student.class, student.getId());

        assertNotNull(retrievedStudent);
        assertEquals(1L, retrievedStudent.getId());
        assertEquals("John", retrievedStudent.getFirstName());
        assertEquals("Doe", retrievedStudent.getLastName());
        assertTrue(retrievedStudent.getIs_active());
        assertEquals(dateTime, retrievedStudent.getCreated_at());
        assertEquals(dateTime, retrievedStudent.getUpdated_at());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testStudentClassesRelationship() {
        Student student = new Student();

        Classes class1 = new Classes();
        Classes class2 = new Classes();

        List<Classes> classes = new ArrayList<>();
        classes.add(class1);
        classes.add(class2);
        student.setClassesList(classes);

        assertEquals(2, student.getClassesList().size());
        assertEquals(class1, student.getClassesList().get(0));
        assertEquals(class2, student.getClassesList().get(1));

    }
}
