package com.access.repo;

import com.access.model.IpSubnetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpSubnetRepo extends JpaRepository<IpSubnetEntity, String> {
}
