package com.access.repo;

import com.access.model.AttackAttemptEntity;
import com.access.model.IpSubnetEntity;

import java.util.List;

public interface AccessBD {
    AttackAttemptEntity findByIpSubnetAndServiceName(IpSubnetEntity ipSubnet, String serviceName);
    AttackAttemptEntity save(AttackAttemptEntity entity);
    IpSubnetEntity findByIpSubnet(String ipSubnet);
    IpSubnetEntity save(IpSubnetEntity entity);
    long getAttackAttemptsCount();
}
