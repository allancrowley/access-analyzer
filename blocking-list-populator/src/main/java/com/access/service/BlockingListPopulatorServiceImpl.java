package com.access.service;

import com.access.dto.AttackAttemptDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.access.model.AttackAttemptEntity;
import com.access.model.IpSubnetEntity;
import org.springframework.stereotype.Service;

import com.access.repo.AttackAttemptRepo;
import com.access.repo.IpSubnetRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class BlockingListPopulatorServiceImpl implements BlockingListPopulatorService {
    final  IpSubnetRepo ipSubnetRepo;
    final AttackAttemptRepo attemptRepo;
    @Override
   @Transactional
    public AttackAttemptDto addAttackAttemptDto(AttackAttemptDto attackAttemptDto) {
        AttackAttemptDto result = null;
        List <AttackAttemptEntity> entities = buildAllAttackAttemptEntity(attackAttemptDto);
        log.debug("get list of entities: {}", entities);
        Optional<IpSubnetEntity> duplicateIpSubnet = ipSubnetRepo.findById(attackAttemptDto.subnet());
        if(duplicateIpSubnet.isEmpty()) {
            result = attackAttemptDto;
            for(AttackAttemptEntity entity: entities){
                checkAndAddAllAttackAttempt(entity);

            }
            addBlockingIpSubnet(attackAttemptDto);

        }
            log.debug("subnet {} already exists", duplicateIpSubnet.get().getIpSubnet());
        return result;
    }
//    add ip subnet to DB
    @Transactional
    protected void addBlockingIpSubnet(AttackAttemptDto attackAttemptDto) {
       IpSubnetEntity newIpSubnetEntity = IpSubnetEntity.builder()
                    .ipSubnet(attackAttemptDto.subnet())
                    .build();
            ipSubnetRepo.save(newIpSubnetEntity);
            log.debug("subnet {} added", newIpSubnetEntity.getIpSubnet());

    }
//    check and add attack attempt entity to DB
    @Transactional
    protected void checkAndAddAllAttackAttempt(AttackAttemptEntity entity) {
       Optional <AttackAttemptEntity> duplicateIpSubnet =
               Optional.ofNullable(attemptRepo.findByIpSubnetAndServiceName(entity.getIpSubnet(), entity.getServiceName()));
       if(duplicateIpSubnet.isEmpty()) {
           attemptRepo.save(entity);
           log.debug("Added attack attempt: {}", entity);
       }else{
           log.warn("attempt already exists: {}", duplicateIpSubnet.get());
       }

    }

    // Method that takes a DTO and creates a list of entities
    private List<AttackAttemptEntity> buildAllAttackAttemptEntity(AttackAttemptDto attackAttemptDto){

        IpSubnetEntity subnet = IpSubnetEntity
                .builder()
                .ipSubnet(attackAttemptDto.subnet())
                .build();

        return attackAttemptDto.services().stream().map(name ->
            AttackAttemptEntity.builder()
                    .serviceName(name)
                    .timestamp(attackAttemptDto.timestamp())
                    .ipSubnet(subnet)
                    .build()
        ).collect(Collectors.toList());
    }
//    static void test(){
//        IpSubnetEntity test = IpSubnetEntity.builder()
//                .ipSubnet("100.100.100")
//                .build();
//        ipSubnetRepo.save(test);
//    }
}
