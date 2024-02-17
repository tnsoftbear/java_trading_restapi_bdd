package org.example.trading_demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trading_demo.model.stored_order.StoredOrder;
import org.example.trading_demo.model.Trade;
import org.example.trading_demo.repository.TradeRepository;
import org.springframework.stereotype.Service;
import org.example.trading_demo.model.stored_order.Type;

@Service
@AllArgsConstructor
@Slf4j
public class TradeService {
    TradeRepository tradeRepository;
    OrderService orderService;

    public Trade tradeWithSeller(StoredOrder sellerOrder) {
        StoredOrder buyerOrder = orderService.findFirstByTypeAndSecurityId(Type.BUYER, sellerOrder.getSecurity().getId());
        if (buyerOrder == null) {
            return null;
        }

        return createTrade(buyerOrder, sellerOrder);
    }

    public Trade tradeWithBuyer(StoredOrder buyerOrder) {
        StoredOrder sellerOrder = orderService.findFirstByTypeAndSecurityId(Type.SELLER, buyerOrder.getSecurity().getId());
        if (sellerOrder == null) {
            return null;
        }

        return createTrade(buyerOrder, sellerOrder);
    }

    private Trade createTrade(StoredOrder buyerOrder, StoredOrder sellerOrder) {
        Trade trade = new Trade();
        trade.setQuantity(Math.min(buyerOrder.getQuantity(), sellerOrder.getQuantity()));
        trade.setPrice(Math.min(buyerOrder.getPrice(), sellerOrder.getPrice()));
        trade.setBuyOrder(buyerOrder);
        trade.setSellOrder(sellerOrder);
        tradeRepository.save(trade);
        orderService.markFulfilled(buyerOrder);
        orderService.markFulfilled(sellerOrder);
        log.info("Trade registered - " + trade);
        return trade;
    }
}
