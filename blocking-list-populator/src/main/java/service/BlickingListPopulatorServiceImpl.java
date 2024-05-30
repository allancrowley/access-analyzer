package service;

import com.access.dto.AttackAttemptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.AttackAttemptEntity;
import model.IpSubnetEntity;
import org.springframework.stereotype.Service;
import repo.AttackAttemptRepo;
import repo.IpSubnetRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlickingListPopulatorServiceImpl implements BlockingListPopulatorService {
    final IpSubnetRepo ipSubnetRepo;
    final AttackAttemptRepo attemptRepo;
    @Override
    public AttackAttemptDto addAttackAttemptDot(AttackAttemptDto attackAttemptDto) {
        Optional <IpSubnetEntity> duplicateIpSubnet =  ipSubnetRepo.findById(attackAttemptDto.subnet());
        if(!duplicateIpSubnet.isPresent()) {
            IpSubnetEntity newIpSubnetEntity = IpSubnetEntity.builder()
                    .ipSubnet(attackAttemptDto.subnet())
                    .build();
            ipSubnetRepo.save(newIpSubnetEntity);
            log.debug("Added ip subnet: {}", newIpSubnetEntity);
        }else{
            log.debug("subnet {} already exists", duplicateIpSubnet.get().getIpSubnet());
        }
        List <AttackAttemptEntity> entities = buildAllAttackAttemptEntity(attackAttemptDto);

        return null;
    }

    private List<AttackAttemptEntity> buildAllAttackAttemptEntity(AttackAttemptDto attackAttemptDto){
        List<AttackAttemptEntity> entities = new ArrayList<>();
        IpSubnetEntity subnet = IpSubnetEntity
                .builder()
                .ipSubnet(attackAttemptDto.subnet())
                .build();

        entities = attackAttemptDto.services().stream().map(name ->
            AttackAttemptEntity.builder()
                    .serviceName(name)
                    .timestamp(attackAttemptDto.timestamp())
                    .ipSubnet(subnet)
                    .build()
        ).collect(Collectors.toList());

        return entities;
    }
}
