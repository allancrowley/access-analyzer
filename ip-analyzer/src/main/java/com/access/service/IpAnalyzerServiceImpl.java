package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.dto.AuthFailureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpAnalyzerServiceImpl implements IpAnalyzerService {
    final StreamBridge streamBridge;
    AttackAttemptDto attackAttemptDto;

    @Override
    public void processAuthFailure(AuthFailureDto dto, String producerBindingName) {
        log.debug("attack attempt data identified: {}", attackAttemptDto);
        streamBridge.send(producerBindingName, attackAttemptDto);
        log.debug("attack attempt data {} sent by {}", attackAttemptDto, producerBindingName);
    }
}
