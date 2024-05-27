package com.access.service;

import com.access.dto.AuthFailureDto;

public interface IpAnalyzerService {
    void processAuthFailure(AuthFailureDto dto, String producerBindingName);
}
