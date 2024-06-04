package com.access.service;

import com.access.dto.AttackAttemptDto;
import org.springframework.stereotype.Service;

@Service
public interface BlockingListPopulatorService {
    AttackAttemptDto addAttackAttemptDto(AttackAttemptDto attackAttemptDto);
}
