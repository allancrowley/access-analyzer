package com.access.repository;

import com.access.util.DataUtils;
import com.access.model.EmailEntity;
import com.access.repo.AccessDb;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
@ComponentScan("com.access.repo")

public class EmailProviderRepositoryTestContainer {
    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest");
    @Autowired
    private AccessDb accessDb;

    @BeforeAll
    public static void setUp() {
        postgresqlContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        postgresqlContainer.stop();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    ImmutableMap.of(
                            "spring.datasource.url", postgresqlContainer.getJdbcUrl(),
                            "spring.datasource.username", postgresqlContainer.getUsername(),
                            "spring.datasource.password", postgresqlContainer.getPassword()
                    )
            ).applyTo(applicationContext.getEnvironment());
        }
    }

    @Test
    @DisplayName("Test find emails functionality using testcontainer")
    public void givenServiceNames_whenFindEmails_thenEmailsAreReturned() {
        //given
        List<String> serviceNames = DataUtils.getServiceNamesList();
        List<String> expectedEmails = DataUtils.getEmailsList();
        List<String> emails = accessDb.findEmailsByServiceNames(serviceNames);
        assertTrue(emails.isEmpty());
        List<EmailEntity> emailEnteties = DataUtils.getEmailEntitiesList();
        accessDb.saveAll(emailEnteties);
        //when
        emails = accessDb.findEmailsByServiceNames(serviceNames);
        //then
        assertThat(emails).isNotNull();
        assertEquals(expectedEmails, emails);
    }
}