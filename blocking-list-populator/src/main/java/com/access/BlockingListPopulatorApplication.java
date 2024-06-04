package com.access;

import com.access.dto.AttackAttemptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import com.access.service.BlockingListPopulatorService;

import java.util.function.Consumer;


@SpringBootApplication(scanBasePackages = "com.access")
@RequiredArgsConstructor
@Slf4j

public class BlockingListPopulatorApplication {

    final BlockingListPopulatorService blockingListPopulatorService;

    public static void main(String[] args) {
        SpringApplication.run(BlockingListPopulatorApplication.class, args);
    }
    @Bean
    Consumer<AttackAttemptDto> blockingListPopulatorConsumer() {
        return this::BlockingIpDtoPopulation;
    }

    private void BlockingIpDtoPopulation(AttackAttemptDto attackAttemptDto){
        log.debug("received attackAttemptDto: {}", attackAttemptDto);
        blockingListPopulatorService.addAttackAttemptDto(attackAttemptDto);
        log.debug("blocking ip: {} and attack attempt {} saved to Database"
                , attackAttemptDto.subnet(), attackAttemptDto);
    }



}
