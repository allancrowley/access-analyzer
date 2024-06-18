package com.access;

import com.access.dto.AuthFailureDto;
import com.access.service.AuthFailureGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AuthFailureGeneratorApplication {
    final AuthFailureGeneratorService authFailureGeneratorService;

    @Value("${app.auth.failures.polling.count}")
    private int pollCount;
    @Value("${spring.integration.poller.fixedDelay}")
    private int delay;
    private volatile static int curCount = 0;


    public static void main(String[] args) throws Exception {
        var ctx = SpringApplication.run(AuthFailureGeneratorApplication.class, args);
        var authFailureGenerator = ctx.getBean(AuthFailureGeneratorApplication.class);

        synchronized (authFailureGenerator) {
            while (authFailureGenerator.pollCount == 0 || curCount < authFailureGenerator.pollCount) {
                authFailureGenerator.wait(authFailureGenerator.delay / 10);
            }
        }
        ctx.close();
    }


    @Bean
    Supplier<AuthFailureDto> failuresSupplier() {
        return () -> buildAuthFailureDto();
    }


    private AuthFailureDto buildAuthFailureDto() {
        curCount++;
        AuthFailureDto authFailureDto = authFailureGeneratorService.getAuthFailure();
        log.warn("{} sent to message broker", authFailureDto);
        return authFailureDto;
    }

}
