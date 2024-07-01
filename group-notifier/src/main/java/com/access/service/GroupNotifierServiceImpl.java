package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.dto.ServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupNotifierServiceImpl implements GroupNotifierService {
    final RestTemplate restTemplate;
    final ServiceConfiguration serviceConfiguration;
    HashMap<String, String> cache = new HashMap<>();

    @Override
    public List<String> getMails(List<String> services) {
        List<String> emails = new ArrayList<>();
        List<String> servicesNotInCache = new ArrayList<>();
        String res;
        for (int i = 0; i < services.size(); i++) {
            res = cache.get(services.get(i));
            if (res == null) {
                log.debug("email for service {} doesn't exist in cache", services.get(i));
                servicesNotInCache.add(services.get(i));
            } else {
                log.debug("email from cache: {}", res);
                emails.add(res);
            }
        }
        if (servicesNotInCache.size() > 0) {
            List<String> emailsNotInCache = emailRequest(servicesNotInCache);
            emails.addAll(emailsNotInCache);
        }
        return emails;
    }

    private List<String> emailRequest(List<String> services) {
        String url = getUrlWithServices(services);
        ResponseEntity<String[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String[].class);
        String[] emailArray = responseEntity.getBody();
        List<String> emailList = Arrays.asList(emailArray);

        for (int i = 0; i < services.size(); i++) {
            String serviceName = services.get(i);
            if (i < emailList.size()) {
                String email = emailList.get(i);
                cache.put(serviceName, email);
                log.debug("Cached email for service {}: {}", serviceName, email);
            } else {
                log.warn("No email found in response for service {}", serviceName);
            }
        }

        return emailList;
    }

    private String getUrlWithServices(List<String> services) {
        StringBuilder sb = new StringBuilder(getUrl());
        sb.append("?services=");
        sb.append(String.join("&services=", services));
        return sb.toString();
    }

    private String getUrl() {
        String url = String.format("http://%s:%d/%s", serviceConfiguration.getHost(), serviceConfiguration.getPort(),
                serviceConfiguration.getPath());
        log.debug("url created is {}", url);
        return url;
    }

    public void updateCache(ServiceDto serviceDto) {
        String serviceName = serviceDto.webserviceName();
        String email = serviceDto.email();
        if (cache.containsKey(serviceName) && email != null) {
            cache.put(serviceName, email);
        }
    }

}