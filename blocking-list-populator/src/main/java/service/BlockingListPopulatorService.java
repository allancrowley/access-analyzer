package service;

import com.access.dto.AttackAttemptDto;
import org.springframework.stereotype.Service;

@Service
public interface BlockingListPopulatorService {
    AttackAttemptDto addAttackAttemptDot(AttackAttemptDto attackAttemptDto);
}
