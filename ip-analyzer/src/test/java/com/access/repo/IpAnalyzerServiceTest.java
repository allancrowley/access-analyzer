package com.access.repo;

import com.access.dto.AuthFailureDto;
import com.access.model.FailureList;
import com.access.service.IpAnalyzerService;
import com.access.util.DataUtils;
import com.access.util.SetupRedisMock;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
public class IpAnalyzerServiceTest {
    @Autowired
    IpAnalyzerService ipAnalyzerService;
    @MockBean
    FailuresCounterRepo failuresCounterRepo;
    HashMap<String, FailureList> redisMockMap = new HashMap<>();

    @BeforeEach
    void mockSetUp() {
        redisMockMap.clear();
        SetupRedisMock.setupRedisMock(failuresCounterRepo, redisMockMap);
    }

    @Test
    @Order(1)
    @DisplayName("Test Failure find by id fail then add current failure to repo ")
    void givenAuthFailureDto_whenFindByIdNotExists_thenAddNewFailureToRepo() {
        //given
        AuthFailureDto authFailureDto = DataUtils.getNotExistsAuthFailureDto();
        //when
        ipAnalyzerService.processAuthFailure(authFailureDto);
        //then
        assertTrue(failuresCounterRepo.existsById(authFailureDto.subnet()));
    }
    @Test
    @Order(2)
    @DisplayName("Test Failure find by id exists and added correct to repo")
    void givenAuthFailureDto_whenFindByIdSuccess_thenAttackAttemptAddedCorrectToRepo() {
        //given
        AuthFailureDto authFailureDto1 = DataUtils.getExistsAuthFailureDto();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AuthFailureDto authFailureDto2 = DataUtils.getExistsAuthFailureDto();
        ipAnalyzerService.processAuthFailure(authFailureDto1);
        //when
        ipAnalyzerService.processAuthFailure(authFailureDto2);
        //then
        Optional<FailureList> failureListWhitTwoAttackAttempt = failuresCounterRepo.findById(authFailureDto1.subnet());
        TreeMap<Long, String> attackAttempt = failureListWhitTwoAttackAttempt.get().getAttemptsMap();
        assertEquals(2, attackAttempt.size());

    }
    @Test
    @Order(3)
    @DisplayName("Test amount of attempt more then threshold value then FailureList delete frome repo and alert send")
    void givenAuthFailureDto_whenAmountOfAttemptMoreThenThresholdValue_thenDeleteFromRepoAndAlertSend() {
        // Given


        AuthFailureDto dto = DataUtils.getOverflowingAuthFailureDto();

        // When
//        IntStream.range(0, 50).forEach(i -> {
//            ipAnalyzerService.processAuthFailure(DataUtils.getOverflowingAuthFailureDto());
//        });
        int counter;
        for(int i = 0; i < 60; i++){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ipAnalyzerService.processAuthFailure(DataUtils.getOverflowingAuthFailureDto());

        }

        Optional<FailureList> failureListWhitTwoAttackAttempt = failuresCounterRepo.findById("100.100.100");
        TreeMap<Long, String> attackAttempt = failureListWhitTwoAttackAttempt.get().getAttemptsMap();
        counter = attackAttempt.size();
        System.out.println(counter);


        ipAnalyzerService.processAuthFailure(DataUtils.getOverflowingAuthFailureDto());
        //then




    }
}
