package com.access.util;

import com.access.dto.AttackAttemptDto;
import com.access.model.AttackAttemptEntity;
import com.access.model.IpSubnetEntity;
import jakarta.persistence.*;

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
                .ipSubnet(getIpSubnetNotExists())
                .serviceName("service3")
                .timestamp(1)
                .build();
    }

    public static AttackAttemptEntity getAttackAttemptPersisted() {
        return AttackAttemptEntity.builder()
                .id(2)
                .ipSubnet(getIpSubnetNotExists())
                .serviceName("service3")
                .timestamp(1)
                .build();
    }


    public static AttackAttemptDto getAttackAttemptDto() {
        return new AttackAttemptDto("100.100.100", 1L, List.of("service"));
    }
}
