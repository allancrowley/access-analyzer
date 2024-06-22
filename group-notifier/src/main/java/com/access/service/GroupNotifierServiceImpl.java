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
        String res;
        for (int i = 0; i < services.size(); i++) {
            res = cache.get(services.get(i));
            if (res == null) {
                log.debug("email for service {} doesn't exist in cache", services.get(i));
                res = emailRequest(services.get(i));
                emails.add(res);
            } else {
                log.debug("email from cache: {}", res);
                emails.add(res);
            }
        }
        return emails;
    }

    private String emailRequest(String serviceName) {
        ResponseEntity<?> responseEntity;
        String url = getUrl() + "?" + serviceName; // Append serviceName as a query parameter
        responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String[].class);
        String[] emailArray = (String[]) responseEntity.getBody();
        List<String> emailList = Arrays.asList(emailArray);
        log.debug("email: {}", emailList.get(0));
        cache.put(serviceName, emailList.get(0));
        return emailList.get(0);
    }

    private String getUrl() {
        String url = String.format("http://%s:%d%s/%d", serviceConfiguration.getHost(), serviceConfiguration.getPort(),
                serviceConfiguration.getPath());
        log.debug("url created is {}", url);
        return url;
    }

    @Bean
    Consumer<ServiceDto> updateEmailsConsumer() {
        return this::updateProcessing;
    }

    void updateProcessing(ServiceDto serviceDto) {
        String serviceName = serviceDto.webserviceName();
        String email = serviceDto.email();
        if (cache.containsKey(serviceName) && email != null) {
            cache.put(serviceName, email);
        }
    }
}
