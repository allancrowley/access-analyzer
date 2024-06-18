package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.dto.AuthFailureDto;
import com.access.model.FailureList;
import com.access.repo.CacheDb;
import com.access.repo.FailuresCounterRepo;
import com.access.util.AbstractCacheContainerTest;
import com.access.util.DataUtils;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Primary;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Primary
@Testcontainers

public class IpAnalyzerServiceTest extends AbstractCacheContainerTest {

    @Autowired
    IpAnalyzerService ipAnalyzerService;

    @Autowired
    FailuresCounterRepo failuresCounterRepo;

    @Autowired
    CacheDb cacheDb;

    @BeforeEach
    void SetUp() {
        failuresCounterRepo.deleteAll();

    }

    @Test
    @DisplayName("Test Failure find by id fail then add current failure to repo ")
    void givenAuthFailureDto_whenFindByIdNotExists_thenAddNewFailureToRepo() {
        //given
        AuthFailureDto authFailureDto = DataUtils.getNotExistsAuthFailureDto();
        //when
        ipAnalyzerService.processAuthFailure(authFailureDto);
        //then
        assertTrue(cacheDb.existsById(authFailureDto.subnet()));
    }

    @Test
    @DisplayName("Test Failure find by id exists and added correct to repo")
    void givenAuthFailureDto_whenFindByIdSuccess_thenAttackAttemptAddedCorrectToRepo() {
        //given
        AuthFailureDto authFailureDto1 = DataUtils.getExistsAuthFailureDto();
        AuthFailureDto authFailureDto2 = DataUtils.getNotExistsAuthFailureDto();
        ipAnalyzerService.processAuthFailure(authFailureDto1);
        //when
        ipAnalyzerService.processAuthFailure(authFailureDto2);
        //then
       FailureList failureListWithTwoAttackAttempt = cacheDb.findById(authFailureDto1.subnet()).orElse(null);
        TreeMap<Long, String> attackAttempt = failureListWithTwoAttackAttempt.getAttemptsMap();

        assertEquals(2, attackAttempt.size());

    }
    @Test
    @DisplayName("Test amount of attempt more then threshold value then FailureList deleted from repo")
    void givenAuthFailureDto_whenAmountOfAttemptMoreThenThresholdValue_thenDeleteFromRepo() throws IOException {
        // Given
        for(int i = 0; i < 9; i++){
            ipAnalyzerService.processAuthFailure(DataUtils.getFullMapAuthFailureDto());
        }
        var cacheFull =  cacheDb.findById("100.100.100").orElse(null);
        //when
        var returnedValue = ipAnalyzerService.processAuthFailure(DataUtils.getFullMapAuthFailureDto());
        var cacheEmpty = cacheDb.findById("100.100.100").orElse(null);
        //then
        //checking size of the map before delete
        assertEquals(cacheFull.getAttemptsMap().size(), 9);
        //checking that the map is empty after processAuthFailure
        assertThat(cacheEmpty).isNull();
        assertThat(returnedValue).isNotNull();



    }
}
