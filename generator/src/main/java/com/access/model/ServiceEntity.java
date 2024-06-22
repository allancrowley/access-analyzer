package com.access.model;


import jakarta.persistence.*;


@Entity
@Table(name = "services")
public class ServiceEntity {
    @Id
    @Column(name = "service_name")
    String serviceName;
    String email;
}
