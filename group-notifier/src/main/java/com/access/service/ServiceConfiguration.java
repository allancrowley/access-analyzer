package com.access.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class ServiceConfiguration {
    @Value("${app.emails.provider.host:localhost}")
    String host;
    @Value("${app.emails.provider.port}")
    int port;
    @Value("${app.emails.provider.url}")
    String path;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}