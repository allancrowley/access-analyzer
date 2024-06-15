package com.access;

import com.access.dto.AttackAttemptDto;
import com.access.model.IpSubnetEntity;
import com.access.repo.AttackAttemptRepo;
import com.access.repo.IpSubnetRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import com.access.service.BlockingListPopulatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@SpringBootApplication(scanBasePackages = "com.access")
@RequiredArgsConstructor
@Slf4j

public class BlockingListPopulatorApplication {
    final IpSubnetRepo ipSubnetRepo;
    final BlockingListPopulatorService blockingListPopulatorService;

    public static void main(String[] args) {
        SpringApplication.run(BlockingListPopulatorApplication.class, args);

    }
    @Bean
    Consumer<AttackAttemptDto> blockingListPopulatorConsumer() {

        return this::BlockingIpDtoPopulation;
    }

    //FIXME method naming
    private void BlockingIpDtoPopulation(AttackAttemptDto attackAttemptDto){
        log.debug("received attackAttemptDto: {}", attackAttemptDto);
        blockingListPopulatorService.addAttackAttemptDto(attackAttemptDto);
        log.debug("blocking ip: {} and attack attempt {} saved to Database"
                , attackAttemptDto.subnet(), attackAttemptDto);
    }



}
