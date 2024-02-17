package org.example.trading_demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trading_demo.model.CustomerOrder;
import org.example.trading_demo.model.Security;
import org.example.trading_demo.model.stored_order.StoredOrder;
import org.example.trading_demo.model.User;
import org.example.trading_demo.model.stored_order.Type;
import org.example.trading_demo.repository.SecurityRepository;
import org.example.trading_demo.repository.StoredOrderRepository;
import org.example.trading_demo.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private StoredOrderRepository orderRepository;
    private UserService userService;
    private SecurityRepository securityRepository;
    public StoredOrder create(int price, int quantity, Type type, Security security, User user) {
        StoredOrder order = new StoredOrder();
        order.setPrice(price);
        order.setQuantity(quantity);
        order.setType(type);
        order.setSecurity(security);
        order.setUser(user);
        order.setFulfilled(false);
        order = orderRepository.save(order);
        log.info("Order registered - " + order);
        return order;
    }

    public StoredOrder create(CustomerOrder customerOrder, Type type) {
        User user = userService.findByUsername(customerOrder.getUserName());
        if (user == null) {
            throw new IllegalArgumentException("User not found by username: " + customerOrder.getUserName());
        }
        Security security = securityRepository.findByName(customerOrder.getSecurityName());
        if (security == null) {
            throw new IllegalArgumentException("Security not found by security: " + customerOrder.getSecurityName());
        }

        return this.create(customerOrder.getPrice(), customerOrder.getQuantity(), type, security, user);
    }

    public StoredOrder findFirstByTypeAndSecurityId(Type type, long securityId) {
        List<StoredOrder> orders = orderRepository.findByTypeAndSecurityIdAndFulfilledIsFalse(type, securityId);
        if (orders.isEmpty()) {
            return null;
        }
        return orders.getFirst();
    }

    public void markFulfilled(StoredOrder order) {
        order.setFulfilled(true);
        orderRepository.save(order);
    }
}
