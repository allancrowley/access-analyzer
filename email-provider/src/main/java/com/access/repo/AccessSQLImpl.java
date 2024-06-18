package com.access.repo;

import com.access.model.EmailEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessSQLImpl implements AccessDb {
    private final ServiceRepo serviceRepo;

    @Override
    public List<String> findEmailsByServiceNames(List<String> serviceNames) {
        return serviceRepo.findEmailsByServiceNameIn(serviceNames);
    }

    @Override
    public void saveAll(List<EmailEntity> emailEntities) {
        serviceRepo.saveAll(emailEntities);
    }

    @Override
    public void deleteAll() {
        serviceRepo.deleteAll();
    }
}
