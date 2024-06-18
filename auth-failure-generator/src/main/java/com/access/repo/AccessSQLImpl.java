package com.access.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessSQLImpl implements AccessDb {
    private final ServiceNamesRepo serviceNamesRepo;


    @Override
    public List<String> getServiceNames() {
        return serviceNamesRepo.findAllServiceNameStringsBy();
    }
}
