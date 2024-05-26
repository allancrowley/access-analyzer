package com.access.repo;

import com.access.model.EmailEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServiceRepo extends JpaRepository<EmailEntity, String> {
        @Query("SELECT e.email FROM EmailEntity e WHERE e.service IN :serviceNames")
        List<String> findEmailsByServiceNameIn(@Param("serviceNames") List<String> serviceNames);

}
