package com.access.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "services")
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @Column(name = "service_name")
    String service;
    String email;
}
