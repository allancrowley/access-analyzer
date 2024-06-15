package com.access.repo;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessDb {

    List<String> findEmailsByServiceNames(List<String> serviceNames);
}
