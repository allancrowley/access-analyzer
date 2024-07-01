package com.access.service;

import com.access.filter.IpFilter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    private final IpFilterService ipFilterService;

    public SecurityConfiguration(IpFilterService ipFilterService) {
        this.ipFilterService = ipFilterService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                new IpFilter(ipFilterService), BasicAuthenticationFilter.class);
        return http.build();
    }
}
