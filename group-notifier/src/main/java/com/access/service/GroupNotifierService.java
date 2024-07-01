package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.dto.ServiceDto;

import java.util.List;

public interface GroupNotifierService {
    List<String> getMails(List<String> services);

    void updateCache(ServiceDto serviceDto);
}
