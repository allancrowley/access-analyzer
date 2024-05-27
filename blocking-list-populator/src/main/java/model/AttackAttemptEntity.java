package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class AttackAttemptEntity {
    @Id
    long id;
    String ipSubnet;
    String service;
    long timestamp;
}
