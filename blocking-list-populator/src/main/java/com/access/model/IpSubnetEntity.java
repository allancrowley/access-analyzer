package com.access.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.*;

@Entity
@Table(name = "ip_addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpSubnetEntity {
    @Id
    private String ipSubnet;
    @OneToMany(mappedBy = "ipSubnet", cascade = CascadeType.ALL)
    private Set<AttackAttemptEntity> attackAttemptEntitySet;
}
