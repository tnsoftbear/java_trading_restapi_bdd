package org.example.trading_demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.trading_demo.model.stored_order.StoredOrder;

@Entity
@Table(name = "trade")
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @OneToOne
    @JoinColumn(name = "sell_order_id", unique = false, nullable = true)
    private StoredOrder sellOrder;
    @OneToOne
    @JoinColumn(name = "buy_order_id", unique = false, nullable = true)
    private StoredOrder buyOrder;
}
