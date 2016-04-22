package com.ruber.service;

import com.ruber.dao.MarketDAO;
import com.ruber.dao.entity.Market;
import com.ruber.dao.entity.Order;
import com.ruber.exception.NoSuchOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional//fixme
public class MarketService {
    @Autowired
    private MarketDAO marketDAO;

    public boolean isMarketExist(Integer vkGroupId) {
        return marketDAO.getByVkGroupId(vkGroupId) != null;
    }

    public Market getMarketByVkGroupId(Integer vkGroupId) {
        return marketDAO.getByVkGroupId(vkGroupId);
    }

    public void addMarket(Integer vkGroupId) {
        Market market = new Market(null, vkGroupId, Collections.emptyList());

        marketDAO.create(market);
    }

    public Order assureAndGetOrderFromMarket(Integer marketVkId, Integer orderId) {
        return getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .findFirst()
            .orElseThrow(() -> new NoSuchOrderException(orderId));
    }
}