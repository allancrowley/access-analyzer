package com.access;

import com.access.dto.AttackAttemptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import service.BlockingListPopulatorService;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class BlockingListPopulatorApplication {
    private final BlockingListPopulatorService service;

    public static void main(String[] args) {
        SpringApplication.run(BlockingListPopulatorApplication.class, args);
    }

    Consumer<AttackAttemptDto> blockingListPopulatorConsumer() {
        return null;
    }

    private void BlockingIpDtoPopulation(AttackAttemptDto attackAttemptDto){

    }



}
