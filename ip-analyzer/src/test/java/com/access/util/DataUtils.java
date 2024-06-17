package com.access.util;

import com.access.dto.AttackAttemptDto;
import com.access.dto.AuthFailureDto;
import com.access.model.FailureList;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DataUtils {

    public static AuthFailureDto getNotExistsAuthFailureDto() {
       return new AuthFailureDto("100.100.100", 1L, "testServiceName");

    }

    public static AuthFailureDto getExistsAuthFailureDto() {
        return new AuthFailureDto("100.100.100", 2L, "testServiceName");
    }

    public static AuthFailureDto getFullMapAuthFailureDto() {
        return new AuthFailureDto("100.100.100", System.currentTimeMillis() , "testServiceName");
    }

    public static AttackAttemptDto getAttackAttemptDto() {
        List<String> services = new ArrayList<>();
        services.add("testServiceName");
        return new AttackAttemptDto("100.100.100", 1L, services);

    }
    public static FailureList getTestFailureList() {
        TreeMap<Long, String> attemptsMap = new TreeMap<>();
        attemptsMap.put(System.currentTimeMillis(), "testServerName");
        return FailureList.builder()
                .subnet("100.100.100")
                .attemptsMap(attemptsMap)
                .build();
    }

    public static FailureList getTestNotExistsFailureList() {
        TreeMap<Long, String> attemptsMap = new TreeMap<>();
        attemptsMap.put(System.currentTimeMillis(), "testServerName1");
        return FailureList.builder()
                .subnet("100.100.200")
                .attemptsMap(attemptsMap)
                .build();
    }

}
