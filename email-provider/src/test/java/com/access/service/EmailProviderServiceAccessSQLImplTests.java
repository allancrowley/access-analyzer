package com.access.service;

import com.access.util.DataUtils;
import com.access.repo.AccessSQLImpl;
import com.access.repo.ServiceRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
public class EmailProviderServiceAccessSQLImplTests {
    @Mock
    private ServiceRepo serviceRepo;
    @InjectMocks
    private AccessSQLImpl serviceUnderTest;

    @Test
    @DisplayName("Test find emails functionality")
    public void givenServiceNames_whenFindEmails_thenEmailsAreReturned() {
        //given
        List<String> serviceNames = DataUtils.getServiceNamesList();
        List<String> expectedEmails = DataUtils.getEmailsList();
        BDDMockito.given(serviceRepo.findEmailsByServiceNameIn(anyList()))
                .willReturn(expectedEmails);
        //when
        List<String> emails = serviceUnderTest.findEmailsByServiceNames(serviceNames);
        //then
        assertThat(emails).isNotNull();
        assertEquals(expectedEmails, emails);
    }
}
