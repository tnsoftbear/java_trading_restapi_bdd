package org.example.trading_demo.repository;

import org.example.trading_demo.model.stored_order.StoredOrder;
import org.example.trading_demo.model.stored_order.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface StoredOrderRepository extends JpaRepository<StoredOrder, Long> {
    List<StoredOrder> findByTypeAndSecurityIdAndFulfilledIsFalse(Type type, long securityId);

    @Transactional
    @Query("select o from StoredOrder o where o.id in (:ids)")
    List<StoredOrder> loadOrdersByIds(@Param("ids") Collection<Long> identifiers);

    // Optimization of deleteAll()
    @Modifying
    @Transactional
    @Query(value = "delete from stored_order", nativeQuery = true)
    void truncate();
}
