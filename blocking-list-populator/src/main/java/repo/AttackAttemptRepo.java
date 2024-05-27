package repo;

import model.AttackAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttackAttemptRepo extends JpaRepository<AttackAttemptEntity, Long> {
}
