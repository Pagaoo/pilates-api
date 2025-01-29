package com.dev.pilates.entities;

import com.dev.pilates.ENUMS.RoleEnum;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class RoleTest {

    @Autowired
    private EntityManager em;

    private LocalDateTime dateTime;
    private Roles role;
    private Roles role2;

    @BeforeEach
    public void setUp() {
        dateTime = LocalDateTime.now();

        role = new Roles();
        role.setRole(RoleEnum.valueOf("ROLE_ADMIN"));
        role.setDescription("Role Admin");
        role.setCreated_at(dateTime);
        role.setUpdated_at(dateTime);
        em.persist(role);


        role2 = new Roles();
        role2.setRole(RoleEnum.valueOf("ROLE_PROFESSOR"));
        role2.setDescription("Role Professor");
        role2.setCreated_at(dateTime);
        role2.setUpdated_at(dateTime);
        em.persist(role2);

        em.flush();
        em.clear();
    }


    @Test
    public void testCreateAndRetrieveRoleAdmin() {

        Roles retrievedRole = em.find(Roles.class, role.getId());

        assertNotNull(retrievedRole);
        assertEquals(RoleEnum.ROLE_ADMIN, retrievedRole.getRole());
        assertEquals("Role Admin", retrievedRole.getDescription());
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedRole.getCreated_at().truncatedTo(ChronoUnit.MILLIS));
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedRole.getUpdated_at().truncatedTo(ChronoUnit.MILLIS));
    }

    @Test
    public void testCreateAndRetrieveRoleProfessor() {

        Roles retrievedRole2 = em.find(Roles.class, role2.getId());

        assertNotNull(retrievedRole2);
        assertEquals(RoleEnum.ROLE_PROFESSOR, retrievedRole2.getRole());
        assertEquals("Role Professor", retrievedRole2.getDescription());
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedRole2.getCreated_at().truncatedTo(ChronoUnit.MILLIS));
        assertEquals(dateTime.truncatedTo(ChronoUnit.MILLIS), retrievedRole2.getUpdated_at().truncatedTo(ChronoUnit.MILLIS));
    }

    @Test
    public void testFindRoleById() {
        Roles retrievedRole = em.createQuery("SELECT r FROM Roles r WHERE r.id = :id", Roles.class)
                .setParameter("id", role2.getId())
                .getSingleResult();

        assertNotNull(retrievedRole);
        assertEquals(RoleEnum.ROLE_PROFESSOR, retrievedRole.getRole());
    }
}
