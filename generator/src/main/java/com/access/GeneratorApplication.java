package com.access;



import com.access.controller.SimulationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.function.Supplier;

@SpringBootApplication
@RestController
@Slf4j
public class GeneratorApplication {
    private final SimulationController simulationController;
    @Value("30")
    private int pollCount;
    @Value("100")
    private int delay;
    private volatile static int curCount = 0;

    public GeneratorApplication(SimulationController simulationController) {
        this.simulationController = simulationController;
    }

    public static void main(String[] args) throws InterruptedException {
        var ctx = SpringApplication.run(GeneratorApplication.class, args);
        var generator = ctx.getBean(GeneratorApplication.class);

       synchronized (generator){
           while(generator.pollCount == 0 || curCount < generator.pollCount){
               generator.wait(generator.delay / 10);
           }
       }
    }


    @Bean
    Supplier<ResponseEntity<String>> responseSupplier() {
        return () -> buildResponse();
    }


    private ResponseEntity<String> buildResponse() {
        curCount++;
        ResponseEntity<String> response = simulationController.runSimulation();
        log.warn("{} sent ", response.getStatusCode());
        return response;
    }
}
