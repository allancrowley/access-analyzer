package com.access.service;

import com.access.config.GeneratorConfig;
import com.access.repo.AccessDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final AccessDb accessDb;

    private final GeneratorConfig config;

    private final RestTemplate restTemplate;

    List<String> cache = new ArrayList<>();




    @Override
    public ResponseEntity<String> getResponse() {
        if(cache.isEmpty()) {
            cache = accessDb.getServiceNames();
        }
        String generatedIp = getRandomIp();
        String randomServiceName = getRandomServiceName();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Forwarded-For", generatedIp);
        headers.set("Service-Name", randomServiceName);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = config.getTargetUrl();
        log.debug("Url: {}, headers: {}", url, httpEntity.getHeaders());
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

    private String getRandomServiceName() {
        int index = ThreadLocalRandom.current().nextInt(config.getMaxServiceNameRange() > cache.size() ? cache.size() : config.getMaxServiceNameRange());

        return cache.get(index);
    }

    private String getRandomIp() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        return "256.256." + rand.nextInt(config.getMinIpNumber(), config.getMaxIpNumber()) + "."
                + rand.nextInt(config.getMinIpNumber(), config.getMaxIpNumber());
    }


}
