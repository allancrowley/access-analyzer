package com.access;

import com.access.dto.AuthFailureDto;
import com.access.service.IpAnalyzerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication(scanBasePackages = "com.access")
@RequiredArgsConstructor
@Slf4j
public class IpAnalyzerApplication {
    final IpAnalyzerService ipAnalyzerService;


    public static void main(String[] args) {
        SpringApplication.run(IpAnalyzerApplication.class, args);
    }

    @Bean
    Consumer<AuthFailureDto> analyzerConsumer() {
        return this::processAuthFailure;
    }

    private void processAuthFailure(AuthFailureDto authFailureDto) {
        log.trace("received authentication failure data: {}", authFailureDto);
        ipAnalyzerService.processAuthFailure(authFailureDto, producerBindingName);
    }
}
