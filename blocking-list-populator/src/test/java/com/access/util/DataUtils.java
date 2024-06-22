package com.access.util;

import com.access.dto.AttackAttemptDto;
import com.access.model.*;
import java.util.List;

public class DataUtils {

    public static IpSubnetEntity getIpSubnetExists() {
        return IpSubnetEntity.builder().ipSubnet("100.100.100").build();
    }

    public static IpSubnetEntity getIpSubnetNotExists() {
        return IpSubnetEntity.builder().ipSubnet("100.100.102").build();
    }


    public static AttackAttemptEntity getAttackAttemptTransient() {
        return AttackAttemptEntity.builder()
                .ipSubnet(getIpSubnetExists())
                .serviceName("service3")
                .timestamp(1)
                .build();
    }

    public static AttackAttemptEntity getAttackAttemptPersisted() {
        return AttackAttemptEntity.builder()
                .id(1)
                .ipSubnet(getIpSubnetExists())
                .serviceName("service1")
                .timestamp(1)
                .build();
    }

    public static AttackAttemptEntity getAttackAttemptIpNotExists() {
        return AttackAttemptEntity.builder()
                .id(1)
                .ipSubnet(getIpSubnetNotExists())
                .serviceName("service1")
                .timestamp(1)
                .build();
    }

    public static AttackAttemptEntity getAttackAttemptServiceNotExists() {
        return AttackAttemptEntity.builder()
                .id(1)
                .ipSubnet(getIpSubnetExists())
                .serviceName("service5")
                .timestamp(1)
                .build();
    }


    public static AttackAttemptDto getAttackAttemptDtoServiceNotExists() {
        return new AttackAttemptDto("100.100.102", 1L, List.of("service"));
    }

    public static AttackAttemptDto getAttackAttemptDtoDifferentServices() {
        return new AttackAttemptDto("100.100.102", 1L, List.of("service1", "service2"));
    }

    public static AttackAttemptDto getAttackAttemptDtoSameServices() {
        return new AttackAttemptDto("100.100.102", 1L, List.of("service1", "service1"));
    }

    public static AttackAttemptDto getAttackAttemptDtoAlreadyAdded() {
        return new AttackAttemptDto("100.100.100", 1L, List.of("service2"));
    }
}
