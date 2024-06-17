package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.dto.AuthFailureDto;

public interface IpAnalyzerService {
    AttackAttemptDto processAuthFailure(AuthFailureDto dto);
}
