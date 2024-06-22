package com.access.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "services")
public class ServiceEntity {
    @Id
    @Column(name = "service_name")
    String serviceName;
    String email;
}
