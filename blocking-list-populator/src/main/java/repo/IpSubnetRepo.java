package repo;

import model.IpSubnetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpSubnetRepo extends JpaRepository<IpSubnetEntity, String> {
}
