package com.access.repository;

import com.access.util.DataUtils;
import com.access.model.EmailEntity;
import com.access.repo.AccessDb;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan("com.access.repo")
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
public class EmailProviderRepositoryTest_H2 {
    @Autowired
    private AccessDb accessDb;

    @BeforeEach
    public void setUp() {
        accessDb.deleteAll();
    }

    @Test
    @DisplayName("Test find emails functionality using H2")
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
