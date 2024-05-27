package com.access.repo;

import com.access.model.FailureList;
import org.springframework.data.repository.CrudRepository;

public interface FailuresCounterRepo extends CrudRepository<FailureList, String> {
}
