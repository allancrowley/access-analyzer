package repo;

import model.AttackAttemptEntity;
import model.IpSubnetEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AttackAttemptRepo extends JpaRepository<AttackAttemptEntity, Long> {
    AttackAttemptEntity findByIpSubnetAndServiceName(IpSubnetEntity ipSubnet, String serviceName);
}
