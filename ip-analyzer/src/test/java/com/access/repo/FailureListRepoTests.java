package com.access.repo;

import com.access.service.IpAnalyzerService;
import com.access.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Optional;
import com.access.model.FailureList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


@SpringBootTest
class FailureListRepoTests {
    @Autowired
    IpAnalyzerService ipAnalyzerService;
    @MockBean
    FailuresCounterRepo failuresCounterRepo;
    HashMap<String, FailureList> redisMockMap = new HashMap<>();
    @BeforeEach
    void mockSetUp() {
        redisMockMap.clear();
        when(failuresCounterRepo.findById(any(String.class)))
                .then(new Answer<Optional<FailureList>>() {
                    @Override
                    public Optional<FailureList> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        String ip = invocationOnMock.getArgument(0);
                        FailureList failureList = redisMockMap.get(ip);
                        return Optional.ofNullable(failureList);
                    }
                });
        when(failuresCounterRepo.save(any(FailureList.class)))
                .then(new Answer<FailureList>() {
                    @Override
                    public FailureList answer(InvocationOnMock invocationOnMock) throws Throwable {
                        FailureList failureList = invocationOnMock.getArgument(0);
                        redisMockMap.put(failureList.getSubnet(), failureList);
                        return failureList;
                    }
                });
        when(failuresCounterRepo.existsById(any(String.class)))
                .then(new Answer<Boolean>() {
                    @Override
                    public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                        String ip = invocationOnMock.getArgument(0);
                        return redisMockMap.containsKey(ip);
                    }
                });
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String ip = invocation.getArgument(0);
                redisMockMap.remove(ip);
                return null;
            }
        }).when(failuresCounterRepo).deleteById(any(String.class));
    }
    //delete, exists, save. find
    @Test
    @DisplayName("Test save FailureList functionality")
    void givenFailureList_whenSaveFailureList_thenSuccess() {
        //given
        FailureList failureToSave = DataUtils.getTestFailureList();
        //when
        FailureList savedFailure = failuresCounterRepo.save(failureToSave);
        //then
        assertEquals(failureToSave, savedFailure);
    }

    @Test
    @DisplayName("Test found by id functionality")
    void givenFailureList_whenFindById_thenSuccess() {
        //given
        FailureList failureToSave = DataUtils.getTestFailureList();
        failuresCounterRepo.save(failureToSave);
        //when
        FailureList foundFailureList =  failuresCounterRepo.findById(failureToSave.getSubnet()).orElse(null);
        //then
        assertEquals(failureToSave, foundFailureList);
    }

    @Test
    @DisplayName("Test not found by id functionality")
    void givenFailureList_whenFindById_thenNotFound() {
        //given
        FailureList failureToSave = DataUtils.getTestFailureList();
        failuresCounterRepo.save(failureToSave);
        //when
        FailureList notExistsFailureList = DataUtils.getTestNotExistsFailureList();
        FailureList foundFailureList = failuresCounterRepo.findById(notExistsFailureList.getSubnet()).orElse(null);
        //then
        assertNull(foundFailureList);
    }

    @Test
    @DisplayName("Test exist by id functionality")
    void givenFailureList_whenExistById_thenSuccess() {
        //given
        FailureList failureToSave = DataUtils.getTestFailureList();
        failuresCounterRepo.save(failureToSave);
        //when
        boolean exists = failuresCounterRepo.existsById(failureToSave.getSubnet());
        //then
        assertTrue(exists);
    }

    @Test
    @DisplayName("Test not exists by id functionality ")
    void givenFailureList_whenNotExistById_thenNotFound() {
        //given
        FailureList failureToSave = DataUtils.getTestFailureList();
        failuresCounterRepo.save(failureToSave);
        //when
        FailureList notExistsFailureList = DataUtils.getTestNotExistsFailureList();
        boolean exists = failuresCounterRepo.existsById(notExistsFailureList.getSubnet());
        //then
        assertFalse(exists);
    }

    @Test
    @DisplayName("Test delete functionality")
    void givenFailureList_whenDeleteFailureList_thenSuccess() {
        //given
        FailureList failureToSave = DataUtils.getTestFailureList();
        failuresCounterRepo.save(failureToSave);
        //when
        failuresCounterRepo.deleteById(failureToSave.getSubnet());
        //then
        assertFalse(failuresCounterRepo.existsById(failureToSave.getSubnet()));

    }
    }


