package repo;

import model.AttackAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttackAttemptRepo extends JpaRepository<AttackAttemptEntity, Long> {
    @Query("SELECT a from AttackAttemptEntity a where a IN :entities")
    List<AttackAttemptEntity> findExistingEntities(@Param("entities") List<AttackAttemptEntity> entities);
}
