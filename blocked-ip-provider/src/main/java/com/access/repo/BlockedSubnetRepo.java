package com.access.repo;

import com.access.model.IpSubnetEntity;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface BlockedSubnetRepo extends JpaRepository<IpSubnetEntity, String> {

    @Query("SELECT ipSubnetEntity.ipSubnet FROM IpSubnetEntity ipSubnetEntity")
    List<String> findAllIpSubnetStringsBy();

}
