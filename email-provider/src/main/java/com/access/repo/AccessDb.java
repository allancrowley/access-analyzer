package com.access.repo;

import com.access.model.EmailEntity;

import java.util.List;

public interface AccessDb {

    List<String> findEmailsByServiceNames(List<String> serviceNames);

    void saveAll(List<EmailEntity> emailEntities);

    void deleteAll();
}
