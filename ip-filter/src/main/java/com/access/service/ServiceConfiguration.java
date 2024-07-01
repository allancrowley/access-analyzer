package com.access.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.access.UrlConstants;

@Configuration
@Getter
public class ServiceConfiguration {
    @Value("${app.ip.provider.host:localhost}")
    String host;
    @Value("${app.ip.provider.port:8080}")
    int port;
    @Value("${app.ip.provider.path:ip}")
    String path;
}
