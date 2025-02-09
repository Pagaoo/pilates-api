package com.dev.pilates.services;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStudentService {


    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void testSaveStudent() {
    }

    @Test
    @Order(2)
    void testFindAllStudents() {
    }

    @Test
    @Order(3)
    void testFindStudentById() {
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
