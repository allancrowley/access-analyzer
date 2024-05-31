package com.access.service;

import com.access.dto.AttackAttemptDto;

import java.util.List;

public interface GroupNotifierService {
    List<String> getMails(List<String> services);
}
