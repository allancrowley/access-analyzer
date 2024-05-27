package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.dto.AuthFailureDto;
import com.access.repo.FailuresCounterRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpAnalyzerServiceImpl implements IpAnalyzerService {
    final StreamBridge streamBridge;
    AttackAttemptDto attackAttemptDto;
    FailuresCounterRepo failuresCounterRepo;
    @Value("${app.analyzer.threshold}")
    int threshold;
    @Value("${app.analyzer.time.period}")
    long timePeriod;

    @Override
    public void processAuthFailure(AuthFailureDto dto, String producerBindingName) {


        log.debug("attack attempt data identified: {}", attackAttemptDto);

    }

    private void IncrementCounter(String ip) {

    }

    private boolean checkThreshold(String ip) {
        return false;
    }

    private void sendAlert(AttackAttemptDto attackAttemptDto, String producerBindingName) {
        streamBridge.send(producerBindingName, attackAttemptDto);
        log.debug("attack attempt data {} sent by {}", attackAttemptDto, producerBindingName);
    }
}
