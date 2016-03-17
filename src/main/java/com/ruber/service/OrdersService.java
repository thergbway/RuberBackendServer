package com.ruber.service;

import com.ruber.controller.dto.AddOrderRequest;
import com.ruber.dao.OrderDAO;
import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.User;
import com.ruber.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional//TODO
public class OrdersService {
    @Autowired
    private RuberTokensService ruberTokensService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrderDAO orderDAO;

    public Integer addOrder(String accessToken, AddOrderRequest addOrderRequest) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        Long createdTimestamp = TimeUtils.getCurrentTimestamp();

        Order order = addOrderRequest.toOrder(createdTimestamp);

        user
            .getOrders()
            .add(order);

        orderDAO.create(order);

        return order.getId();
    }
}
