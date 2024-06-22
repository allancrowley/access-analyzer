package com.access.repo;

import com.access.model.AttackAttemptEntity;
import com.access.model.IpSubnetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessPostgreSQLDB implements AccessBD{
    private final AttackAttemptRepo attemptRepo;
    private final IpSubnetRepo ipSubnetRepo;
    @Override
    public AttackAttemptEntity findByIpSubnetAndServiceName(IpSubnetEntity ipSubnet, String serviceName) {
        return attemptRepo.findByIpSubnetAndServiceName(ipSubnet, serviceName);
    }


    @Override
    public long getAttackAttemptsCount() {
        return attemptRepo.count();
    }

    @Override
    public AttackAttemptEntity save(AttackAttemptEntity entity) {
        return attemptRepo.save(entity);
    }

    @Override
    public IpSubnetEntity findByIpSubnet(String ipSubnet) {
        return ipSubnetRepo.findById(ipSubnet).orElse(null);
    }

    @Override
    public IpSubnetEntity save(IpSubnetEntity entity) {
        return ipSubnetRepo.save(entity);
    }

}
