package com.access.repo;

import com.access.util.DataUtils;
import com.access.util.SetupRedisMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;

import com.access.model.FailureList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class FailureListRepoTests {

    @MockBean
    FailuresCounterRepo failuresCounterRepo;
    HashMap<String, FailureList> redisMockMap = new HashMap<>();
    @BeforeEach
    void mockSetUp() {
        SetupRedisMock.setupRedisMock(failuresCounterRepo, redisMockMap);
    }

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


