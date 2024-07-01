package com.access.repo;

import com.access.model.IpSubnetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessSQLImpl implements AccessDb {
    private final BlockedSubnetRepo blockedSubnetRepo;


    @Override
    public List<String> getBlockedList() {
        return blockedSubnetRepo.findAllIpSubnetStringsBy();
    }

    @Override
    public void deleteAll() {
        blockedSubnetRepo.deleteAll();
    }

    @Override
    public void saveAll(List<IpSubnetEntity> ipSubnetEntities) {
        blockedSubnetRepo.saveAll(ipSubnetEntities);
    }
}
