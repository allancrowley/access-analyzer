package com.access.repo;

import com.access.model.FailureList;
import com.access.repo.FailuresCounterRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CacheRedisImpl implements CacheDb {
    private final FailuresCounterRepo failuresCounterRepo;

    @Override
    public Optional<FailureList> findById(String subnet) {
        return failuresCounterRepo.findById(subnet);
    }

    @Override
    public FailureList save(FailureList failureList) {
        return failuresCounterRepo.save(failureList);
    }

    @Override
    public Boolean existsById(String subnet) {
        return failuresCounterRepo.existsById(subnet);
    }

    @Override
    public void deleteById(String subnet) {
        failuresCounterRepo.deleteById(subnet);
    }
}
