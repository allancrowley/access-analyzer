package com.access.util;

import com.access.model.FailureList;

import java.util.TreeMap;

public class DataUtils {


    public static FailureList getTestFailureList() {
        TreeMap<Long, String> attemptsMap = new TreeMap<>();
        attemptsMap.put(1L, "testServerName");
        return FailureList.builder()
                .subnet("100.100.100")
                .attemptsMap(attemptsMap)
                .build();
    }

    public static FailureList getTestNotExistsFailureList() {
        TreeMap<Long, String> attemptsMap = new TreeMap<>();
        attemptsMap.put(2L, "testServerName1");
        return FailureList.builder()
                .subnet("100.100.200")
                .attemptsMap(attemptsMap)
                .build();
    }

}
