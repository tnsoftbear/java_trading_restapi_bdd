package org.example.trading_demo.repository;

import org.example.trading_demo.model.stored_order.StoredOrder;
import org.example.trading_demo.model.stored_order.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoredOrderRepository extends JpaRepository<StoredOrder, Long> {
    List<StoredOrder> findByTypeAndSecurityIdAndFulfilledIsFalse(Type type, long securityId);
}
