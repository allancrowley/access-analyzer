package com.access.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
public class configuration {
    @Value("10")
    int minIpNumber;

    @Value("20")
    int maxIpNumber;

    @Value("5")
    int maxServiceNameRange;

    @Value("${target.url}")
    String targetUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
