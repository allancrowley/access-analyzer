package com.access.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AuthFailureGeneratorConfig {
    @Value("${app.auth.failures.min.subnet}")
    private int minSubnetNumber;
    @Value("${app.auth.failures.max.subnet}")
    private int maxSubnetNumber;
    @Value("${app.auth.failures.max.service.range}")
    private int maxServiceNameRange;
}
