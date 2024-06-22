package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.repo.AccessBD;
import com.access.repo.AccessPostgreSQLDB;
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
    final AccessBD accessBD;
    @Override

    public AttackAttemptDto addAttackAttemptDto(AttackAttemptDto attackAttemptDto) {
        AttackAttemptDto result = null;
        List <AttackAttemptEntity> entities = buildAllAttackAttemptEntity(attackAttemptDto);
        log.debug("get list of entities: {}", entities);
        IpSubnetEntity duplicateIpSubnet = accessBD.findByIpSubnet(attackAttemptDto.subnet());
        if (duplicateIpSubnet == null) {
            result = attackAttemptDto;
            addBlockingIpSubnet(attackAttemptDto);
            for(AttackAttemptEntity entity: entities) {
                checkAndAddAllAttackAttempt(entity);
            }
        } else {
            log.debug("subnet {} already exists", duplicateIpSubnet.getIpSubnet());
        }
        return result;
    }
//    add ip subnet to DB

    protected void addBlockingIpSubnet(AttackAttemptDto attackAttemptDto) {
       IpSubnetEntity newIpSubnetEntity = IpSubnetEntity.builder()
                    .ipSubnet(attackAttemptDto.subnet())
                    .build();
            accessBD.save(newIpSubnetEntity);
            log.debug("subnet {} added", newIpSubnetEntity.getIpSubnet());

    }
//    check and add attack attempt entity to DB

    protected void checkAndAddAllAttackAttempt(AttackAttemptEntity entity) {
       Optional <AttackAttemptEntity> duplicateIpSubnet =
               Optional.ofNullable(accessBD.findByIpSubnetAndServiceName(entity.getIpSubnet(), entity.getServiceName()));
       if(duplicateIpSubnet.isEmpty()) {

           accessBD.save(entity);
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

}
