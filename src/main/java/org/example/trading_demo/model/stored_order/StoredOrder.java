package org.example.trading_demo.model.stored_order;

import jakarta.persistence.*;
import lombok.*;
import org.example.trading_demo.model.Security;
import org.example.trading_demo.model.User;


@Entity
@Table(name = "stored_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StoredOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "type")
    private Type type;
    @Column(name = "fulfilled")
    private Boolean fulfilled;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "security_id")
    private Security security;
}
