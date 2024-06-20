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


}
