package com.access;




import com.access.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class GeneratorApplication {
    private final GeneratorService generatorService;
    @Value("${app.generator.poll.count}")
    private int pollCount;
    private volatile static int curCount = 0;



    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(GeneratorApplication.class, args);

    }

    @Scheduled(fixedDelayString = "${app.generator.delay}")
    public void poll() {
        if (curCount < pollCount || pollCount == -1) {
            buildResponse();
        }else {
            System.exit(0);
        }
    }

    private ResponseEntity<String> buildResponse() {
        curCount++;
        ResponseEntity<String> response = generatorService.getResponse();
        log.warn("{} sent ", response.getStatusCode());
        return response;
    }
}
