package com.access.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attack_attempts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AttackAttemptEntity {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "ip_subnet", nullable = false)
    private IpSubnetEntity ipSubnet;
    @Column(name = "service_name", nullable = false)
    private String serviceName;
    @Column(nullable = false)
    private long timestamp;
}
