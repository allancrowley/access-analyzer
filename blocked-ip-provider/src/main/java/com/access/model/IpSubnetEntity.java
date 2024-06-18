package com.access.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "ip_addresses")
@NoArgsConstructor
public class IpSubnetEntity {
    @Id
    private String ipSubnet;
}
