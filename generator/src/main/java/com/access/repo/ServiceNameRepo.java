package com.access.repo;

import com.access.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceNameRepo extends JpaRepository<ServiceEntity, String> {
    @Query("SELECT s.serviceName FROM ServiceEntity s")
    List<String> findAllServiceNames();

}
