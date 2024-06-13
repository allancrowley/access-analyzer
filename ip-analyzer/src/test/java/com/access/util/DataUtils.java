package com.access.util;

import com.access.dto.AuthFailureDto;
import com.access.model.FailureList;

import java.util.TreeMap;

public class DataUtils {

    public static AuthFailureDto getNotExistsAuthFailureDto() {
       return new AuthFailureDto("100.100.200", System.currentTimeMillis(), "testServiceName");

    }

    public static AuthFailureDto getExistsAuthFailureDto() {
        return new AuthFailureDto("100.100.100", System.currentTimeMillis(), "testServiceName");
    }

    public static AuthFailureDto getOverflowingAuthFailureDto() {
        return new AuthFailureDto("100.100.100", System.currentTimeMillis(), "testServiceName");
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
