package com.dev.pilates.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class ClassesTest {

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testFindAndRetrieveClasses() {
    }
}
