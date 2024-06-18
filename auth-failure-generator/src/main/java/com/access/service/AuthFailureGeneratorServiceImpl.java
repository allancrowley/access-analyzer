package com.access.service;

import com.access.config.AuthFailureGeneratorConfig;
import com.access.dto.AuthFailureDto;
import com.access.repo.AccessDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthFailureGeneratorServiceImpl implements AuthFailureGeneratorService {
    private final AccessDb accessDb;
    private final AuthFailureGeneratorConfig config;
    private List<String> cache;

    @Override
    public AuthFailureDto getAuthFailure() {
        if (Objects.isNull(cache)) {
            cache = accessDb.getServiceNames();
            log.debug("Cache of service names extracted from DB, names count: {}", cache.size());
        }
        log.trace("Creating AuthFailureDto with subnet in range: {}-{}, service name in range: {}-{}",
                config.getMinSubnetNumber(), config.getMaxSubnetNumber(), 0, config.getMaxServiceNameRange());
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        String randomService = cache.get(rand.nextInt(0, Math.min(cache.size(), config.getMaxServiceNameRange())));
        String randomSubnet = getSubnet();


        AuthFailureDto authFailure = new AuthFailureDto(randomSubnet, System.currentTimeMillis(), randomService);
        log.debug("AuthFailureDto created: {}", authFailure);
        return authFailure;
    }

    private String getSubnet() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        return "256.256." + rand.nextInt(config.getMinSubnetNumber(), config.getMaxSubnetNumber());
    }
}
