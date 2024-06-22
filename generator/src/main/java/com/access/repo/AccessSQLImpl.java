package com.access.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AccessSQLImpl implements AccessDb{
    @Autowired
    ServiceNameRepo serviceNameRepo;
    @Override
    public List<String> getServiceNames() {
        return serviceNameRepo.findAllServiceNames();
    }
}
