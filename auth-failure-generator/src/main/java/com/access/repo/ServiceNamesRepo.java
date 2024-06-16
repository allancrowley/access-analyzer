package com.access.repo;

import com.access.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceNamesRepo extends JpaRepository<ServiceEntity, String> {
    @Query("SELECT serviceEntity.service FROM ServiceEntity serviceEntity")
    List<String> findAllServiceNameStringsBy();

}
