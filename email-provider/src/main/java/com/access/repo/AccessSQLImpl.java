package com.access.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessSQLImpl implements AccessDb {
    private final ServiceRepo serviceRepo;

    @Override
    public List<String> findEmailsByServiceNames(List<String> serviceNames) {
        return serviceRepo.findEmailsByServiceNameIn(serviceNames);
    }
}
