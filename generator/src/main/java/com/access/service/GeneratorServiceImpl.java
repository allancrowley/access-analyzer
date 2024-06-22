package com.access.service;

import com.access.config.configuration;
import com.access.model.DataDto;
import com.access.repo.AccessDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final AccessDb accessDb;

    private final configuration config;

    List<String> cache = new ArrayList<>();


    @Override
    public ResponseEntity<String> getToken() {
        if(cache.isEmpty()) {
            cache = accessDb.getServiceNames();
        }
        String generatedIp = getRandomIp();
        String randomServiceName = getRandomServiceName();
        DataDto dto = new DataDto(generatedIp, randomServiceName);
        return  send(dto);
    }

    private String getRandomServiceName() {
        int index = ThreadLocalRandom.current().nextInt(config.getMaxServiceNameRange());
        return cache.get(index);
    }

    private String getRandomIp() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        return "256.256.256" + rand.nextInt(config.getMinIpNumber(), config.getMaxIpNumber());
    }

    private ResponseEntity<String> send(DataDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Service-Name", dto.serviceName());
        headers.set("Ip-Address", dto.ip());
        HttpEntity<DataDto> httpEntity = new HttpEntity<>(dto, headers);
        return config.restTemplate().postForEntity(config.getTargetUrl(), httpEntity, String.class);
    }
}
