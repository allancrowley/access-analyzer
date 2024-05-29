package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "ip_addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpSubnetEntity {
    @Id
    private String ipSubnet;
    @OneToMany(mappedBy = "ipSubnet")
    private Set<AttackAttemptEntity> attackAttemptEntitySet;
}
