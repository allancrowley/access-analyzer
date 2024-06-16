package com.access.emailprovider.repository;

import com.access.model.EmailEntity;
import com.access.repo.AccessDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan("com.access.repo")
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
public class EmailProviderRepositoryTest {
    @Autowired
    private AccessDb accessDb;

    @BeforeEach
    public void setUp() {
        accessDb.deleteAll();
    }

    @Test
    @DisplayName("Test find emails functionality")
    public void givenServiceNames_whenFindEmails_thenEmailsAreReturned() {
        //given
        List<EmailEntity> emailEnteties = Arrays.asList(
                new EmailEntity("Service1", "service1@example.com"),
                new EmailEntity("Service2", "service2@example.com"),
                new EmailEntity("Service3", "service3@example.com"),
                new EmailEntity("Service4", "service4@example.com"),
                new EmailEntity("Service5", "service5@example.com")
        );
        accessDb.saveAll(emailEnteties);
        List<String> serviceNames = Arrays.asList("Service1", "Service2", "Service3", "Service4", "Service5");
        List<String> expectedEmails = Arrays.asList("service1@example.com", "service2@example.com", "service3@example.com", "service4@example.com", "service5@example.com");
        //when
        List<String> emails = accessDb.findEmailsByServiceNames(serviceNames);
        //then
        assertEquals(expectedEmails, emails);
    }
}
