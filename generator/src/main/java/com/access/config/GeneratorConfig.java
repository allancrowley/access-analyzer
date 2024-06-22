package com.access.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
public class GeneratorConfig {
    @Value("${app.generator.min.ip.number}")
    int minIpNumber;

    @Value("${app.generator.max.ip.number}")
    int maxIpNumber;

    @Value("${app.generator.max.service.name.range}")
    int maxServiceNameRange;

    @Value("${app.generator.target.url}")
    String targetUrl;



    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
