package com.access.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity {
    @Id
    @Column(name = "service_name")
    String service;
    String email;
}
