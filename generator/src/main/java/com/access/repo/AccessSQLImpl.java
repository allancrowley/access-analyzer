package com.access.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class AccessSQLImpl implements AccessDb{
    private final ServiceNameRepo serviceNameRepo;
    @Override
    public List<String> getServiceNames() {
        return serviceNameRepo.findAllServiceNames();
    }
}
