package com.access.repo;

import com.access.model.FailureList;

import java.util.Optional;

public interface CacheDb {
    Optional<FailureList> findById(String subnet);

    FailureList save(FailureList failureList);

    Boolean existsById(String subnet);

    void deleteById(String subnet);
}
