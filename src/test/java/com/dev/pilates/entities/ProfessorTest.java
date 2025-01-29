package com.dev.pilates.entities;

import com.dev.pilates.ENUMS.RoleEnum;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class ProfessorTest {

    @Autowired
    private EntityManager em;

    private Professor professor;
    private LocalDateTime dateTime;
    private Roles role;

    @BeforeEach
    public void setUp() {
        dateTime = LocalDateTime.now();

        role = new Roles();
        role.setRole(RoleEnum.valueOf("ROLE_PROFESSOR"));
        role.setDescription("Role Professor");
        role.setCreated_at(dateTime);
        role.setUpdated_at(dateTime);
        em.persist(role);


        professor = new Professor();

        professor.setUsername("Test");
        professor.setPassword("Test");
        professor.setEmail("test@hotmail.com");
        professor.setRole(role);
        professor.setCreated_at(dateTime);
        professor.setUpdated_at(dateTime);
        em.persist(professor);

        em.flush();
        em.clear();
    }

    @Test
    public void testFindAndRetrieveProfessor() {
        Professor retrievedProfessor = em.find(Professor.class, professor.getId());

        assertNotNull(retrievedProfessor);
        assertNotNull(retrievedProfessor.getId());
        assertEquals("Test", retrievedProfessor.getUsername());
        assertEquals("Test", retrievedProfessor.getPassword());
        assertEquals("test@hotmail.com", retrievedProfessor.getEmail());
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedProfessor.getCreated_at().truncatedTo(ChronoUnit.MILLIS));
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedProfessor.getUpdated_at().truncatedTo(ChronoUnit.MILLIS));

    }
}
