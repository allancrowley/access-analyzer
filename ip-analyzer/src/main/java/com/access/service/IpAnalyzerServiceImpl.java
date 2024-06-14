package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.dto.AuthFailureDto;
import com.access.model.FailureList;
import com.access.repo.CacheDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpAnalyzerServiceImpl implements IpAnalyzerService {
    private final StreamBridge streamBridge;
    @Value("${app.analyzer.producer.binding.name}")
    private String producerBindingName;
    private final CacheDb cacheDb;
    @Value("${app.analyzer.threshold}")
    private int threshold;
    @Value("${app.analyzer.time.period}")
    private long timePeriod;

    @Override
    public void processAuthFailure(AuthFailureDto dto) {
        // Retrieve the FailureList for the given subnet
        Optional<FailureList> optionalFailureList = cacheDb.findById(dto.subnet());
        FailureList failureList;
        if (optionalFailureList.isPresent()) {
            // If FailureList exists, process the authentication failure
            failureList = optionalFailureList.get();
            attemptsMapProcessing(failureList, dto);
        } else {
            // If FailureList does not exist, create a new one and save it to the repository
            cacheDb.save(new FailureList(dto.subnet(), new TreeMap<>(Map.of(dto.timestamp(), dto.webserviceName()))));
        }
    }

    private void attemptsMapProcessing(FailureList failureList, AuthFailureDto dto) {
        TreeMap<Long, String> attemptsMap = failureList.getAttemptsMap();
        // Filter the attemptsMap to include only entries within the specified time period
        NavigableMap<Long, String> recentAttempts = attemptsMap.tailMap(System.currentTimeMillis() - timePeriod, true);
        if (recentAttempts.size() < threshold - 1) {
            // If the number of authentication attempts is below the threshold, add the new attempt
            attemptsMap.put(dto.timestamp(), dto.webserviceName());
            // Save the updated FailureList back to the repository
            cacheDb.save(failureList);
            log.debug("Failed authentication attempt for service {} from IP {} registered in cache at {}", dto.webserviceName(), dto.subnet(), dto.timestamp());
        } else {
            if (cacheDb.existsById(dto.subnet())) {
                // Delete the FailureList from the repository
                cacheDb.deleteById(dto.subnet());
                log.debug("Failed authentication attempts from IP {} equal to {} and data is deleted from cache and sent to database", dto.subnet(), recentAttempts.size());
                // If the number of authentication attempts exceeds the threshold, send an alert
                sendAlert(recentAttempts, dto.subnet());
            } else {
                log.warn("Attempted to delete non-existing entity with subnet {}", dto.subnet());
            }
        }
    }

    private void sendAlert(NavigableMap<Long, String> recentAttempts, String subnet) {
        // Convert the services in attemptsMap to an array
        List<String> services = new ArrayList<>(recentAttempts.values());
        // Create an AttackAttemptDto for the alert
        AttackAttemptDto attackAttemptDto = new AttackAttemptDto(subnet, recentAttempts.lastKey(), services);
        log.debug("New attack attempt data object created {}", attackAttemptDto);
        // Send the alert using StreamBridge
        streamBridge.send(producerBindingName, attackAttemptDto);
        log.debug("Attack attempt data {} sent by {}", attackAttemptDto, producerBindingName);
    }
}
