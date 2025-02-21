package com.dev.pilates.suite;

import com.dev.pilates.services.TestClassesService;
import com.dev.pilates.services.TestProfessorService;
import com.dev.pilates.services.TestRoleService;
import com.dev.pilates.services.TestStudentService;
import org.junit.jupiter.api.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class TestSuite {

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class rolesTestNested extends TestRoleService {}

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class professorTestNested extends TestProfessorService {}

    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class studentTestNested extends TestStudentService {}

    @Nested
    @Order(4)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class classesTestNested extends TestClassesService {
    }

}
