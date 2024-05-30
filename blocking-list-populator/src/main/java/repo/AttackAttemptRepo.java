package repo;

import model.AttackAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttackAttemptRepo extends JpaRepository<AttackAttemptEntity, Long> {
    List<AttackAttemptEntity> findByServiceNameAndIpSubnetIn(String serviceName, List<String> ipSubnet);
}
