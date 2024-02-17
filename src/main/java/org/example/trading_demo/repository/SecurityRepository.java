package org.example.trading_demo.repository;

import org.example.trading_demo.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityRepository extends JpaRepository<Security, Long> {
    Security findByName(String name);
}
