package com.access.repo;

import com.access.model.AttackAttemptEntity;
import com.access.model.IpSubnetEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AttackAttemptRepo extends JpaRepository<AttackAttemptEntity, Long> {
    AttackAttemptEntity findByIpSubnetAndServiceName(IpSubnetEntity ipSubnet, String serviceName);
}
