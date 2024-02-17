package org.example.trading_demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "security")
@Data
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
}
