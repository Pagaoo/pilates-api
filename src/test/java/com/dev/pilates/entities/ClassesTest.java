package com.dev.pilates.entities;

import com.dev.pilates.ENUMS.ClassesHoursEnum;
import com.dev.pilates.ENUMS.RoleEnum;
import com.dev.pilates.ENUMS.WeekDaysEnum;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class ClassesTest {

    @Autowired
    private EntityManager em;

    private Classes classes;
    private final LocalDateTime dateTime = LocalDateTime.now();

    @BeforeEach
    public void setUp() {

        Roles role = createOrGetRole(RoleEnum.valueOf("ROLE_PROFESSOR"), "Role Professor");
        Professor professor = createOrGetProfessor("professor", "123", "professor@gmail.com", role);

        classes = new Classes();
        classes.setProfessor(professor);
        classes.setWeekday(WeekDaysEnum.valueOf("Terça"));
        classes.setClass_hour(ClassesHoursEnum.valueOf("DEZENOVE_HORAS"));
        classes.setStudents(new ArrayList<>());
        classes.setCreated_at(dateTime);
        classes.setUpdated_at(dateTime);
        em.persist(classes);

        em.flush();
        em.clear();
    }

    @Test
    public void testFindAndRetrieveClasses() {
        Classes retrievedClasses = em.find(Classes.class, classes.getId());

        assertNotNull(retrievedClasses);
        assertEquals(classes.getProfessor().getId(), retrievedClasses.getProfessor().getId());
        assertEquals(ClassesHoursEnum.valueOf("DEZENOVE_HORAS"), retrievedClasses.getClass_hour());
        assertEquals(WeekDaysEnum.valueOf("Terça"), retrievedClasses.getWeekday());
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedClasses.getCreated_at().truncatedTo(ChronoUnit.MILLIS));
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedClasses.getUpdated_at().truncatedTo(ChronoUnit.MILLIS));

    }

    private Professor createOrGetProfessor(String username, String password, String email, Roles role) {
        return em.createQuery("select p from Professor p where p.username = :username", Professor.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElseGet(() -> {
                    Professor newProfessor = new Professor();
                    newProfessor.setUsername(username);
                    newProfessor.setPassword(password);
                    newProfessor.setEmail(email);
                    newProfessor.setRole(role);
                    newProfessor.setCreated_at(dateTime);
                    newProfessor.setUpdated_at(dateTime);
                    em.persist(newProfessor);
                    em.flush();
                    return newProfessor;
                });
    }

    private Roles createOrGetRole(RoleEnum roleEnum, String description) {
        return em.createQuery("select r from Roles r where r.role = :role", Roles.class)
                .setParameter("role", roleEnum)
                .getResultStream()
                .findFirst()
                .orElseGet(() -> {
                    Roles newRole = new Roles();
                    newRole.setRole(roleEnum);
                    newRole.setDescription(description);
                    newRole.setCreated_at(dateTime);
                    newRole.setUpdated_at(dateTime);
                    em.persist(newRole);
                    em.flush();
                    return newRole;
                });
    }
}
