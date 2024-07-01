package com.access.repo;

import com.access.model.IpSubnetEntity;

import java.util.List;

public interface AccessDb {

    List<String> getBlockedList();

    void deleteAll();
    void saveAll(List<IpSubnetEntity> ipSubnetEntities);
}
