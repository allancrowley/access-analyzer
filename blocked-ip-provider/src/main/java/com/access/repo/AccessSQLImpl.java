package com.access.repo;

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
}
