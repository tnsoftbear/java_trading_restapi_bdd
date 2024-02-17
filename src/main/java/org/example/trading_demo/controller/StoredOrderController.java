package org.example.trading_demo.controller;

import lombok.AllArgsConstructor;
import org.example.trading_demo.model.CustomerOrder;
import org.example.trading_demo.model.stored_order.StoredOrder;
import org.example.trading_demo.model.Trade;
import org.example.trading_demo.model.stored_order.Type;
import org.example.trading_demo.repository.StoredOrderRepository;
import org.example.trading_demo.service.OrderService;
import org.example.trading_demo.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class StoredOrderController {
    private final StoredOrderRepository storedOrderRepository;
    private final OrderService orderService;
    private final TradeService tradeService;

    @GetMapping
    public List<StoredOrder> findAll() { return storedOrderRepository.findAll(); }

    @GetMapping("/find_by_ids/{ids}")
    public List<StoredOrder> findByIds(@PathVariable Collection<Long> ids) {
        return storedOrderRepository.loadOrdersByIds(ids);
    }

    @PostMapping("sell")
    public StoredOrder registerSellOrder(@RequestBody CustomerOrder customerOrder) {
        return orderService.create(customerOrder, Type.SELLER);
    }

    @PostMapping("buy")
    public StoredOrder registerBuyOrder(@RequestBody CustomerOrder customerOrder) {
        return orderService.create(customerOrder, Type.BUYER);
    }

    @PostMapping("sell_and_trade")
    public Trade registerSellOrderAndTrade(@RequestBody CustomerOrder customerOrder) {
        StoredOrder sellOrder = orderService.create(customerOrder, Type.SELLER);
        return tradeService.tradeWithSeller(sellOrder);
    }

    @PostMapping("buy_and_trade")
    public Trade registerBuyOrderAndTrade(@RequestBody CustomerOrder customerOrder) {
        StoredOrder buyOrder = orderService.create(customerOrder, Type.BUYER);
        return tradeService.tradeWithBuyer(buyOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<StoredOrder> storedOrderOpt = storedOrderRepository.findById(id);
        storedOrderOpt.ifPresent(storedOrderRepository::delete);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/truncate")
    public ResponseEntity<?> truncate() {
        storedOrderRepository.truncate();
        return ResponseEntity.noContent().build();
    }
}
