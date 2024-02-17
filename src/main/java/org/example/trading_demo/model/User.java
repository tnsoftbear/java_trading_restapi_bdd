package org.example.trading_demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stored_user")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
}
