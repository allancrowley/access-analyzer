package service;

import com.access.dto.AttackAttemptDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repo.AttackAttemptRepo;
import repo.IpSubnetRepo;

@Service
@RequiredArgsConstructor
public class BlickingListPopulatorServiceImpl implements BlockingListPopulatorService {
    final IpSubnetRepo ipSubnetRepo;
    final AttackAttemptRepo attemptRepo;
    @Override
    public AttackAttemptDto addAttackAttemptDot(AttackAttemptDto attackAttemptDto) {

        return null;
    }
}
