package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.dto.AuthFailureDto;
import com.access.model.FailureList;
import com.access.repo.FailuresCounterRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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
        Optional<FailureList> optionalFailureList = failuresCounterRepo.findById(dto.subnet());
        if (optionalFailureList.isPresent()) {
            TreeMap<Long, String> attemptsMap = optionalFailureList.get().getAttemptsMap();
            attemptsMapProcessing(attemptsMap);
        } else {
            failuresCounterRepo.save(new FailureList(dto.subnet(), new TreeMap<>(Map.of(dto.timestamp(), dto.webserviceName()))));
            log.debug("failed authentication attempt for service {} from ip {} registered at {}", dto.webserviceName(), dto.subnet(), dto.timestamp());
        }
        log.debug("attack attempt data identified: {}", attackAttemptDto);

    }

    private void attemptsMapProcessing(TreeMap<Long, String> attemptsMap) {
        if(attemptsMap.size()>=50){
  }
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
