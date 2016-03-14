package com.ruber.service;

import com.ruber.controller.dto.AddOrderRequest;
import com.ruber.dao.OrdersDAO;
import com.ruber.dao.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    @Autowired
    private AuthService authService;

    @Autowired
    private OrdersDAO ordersDAO;

    public Integer addOrder(String accessToken, AddOrderRequest orderInfo) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        Integer userId = authService.getUserId(accessToken);

        Order order = Order.buildFromAddOrderRequest(orderInfo, userId);

        ordersDAO.create(order);

        return order.getId();
    }

    public Order getOrder(String accessToken, Integer orderId) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        Integer userId = authService.getUserId(accessToken);

        Order order = ordersDAO.read(orderId);

        if (!order.getOwnerId().equals(userId))
            throw new RuntimeException("Order does not belong to this user");

        return order;
    }
}
