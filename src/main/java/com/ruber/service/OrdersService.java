package com.ruber.service;

import com.ruber.controller.dto.AddOrderRequest;
import com.ruber.controller.dto.GetOrderResponse;
import com.ruber.controller.dto.OrderPreview;
import com.ruber.dao.OrderDAO;
import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.User;
import com.ruber.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public GetOrderResponse getOrder(String accessToken, Integer orderId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        //TODO it is better to create a named query that gets order by Id for the specified user
        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new RuntimeException(String.format("Order with id = %d for this user does not exist", orderId));

        Order order = orders.get(0);

        return GetOrderResponse.buildFromOrder(order);
    }

    public List<OrderPreview> getOrdersPreview(String accessToken) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        return user
            .getOrders()
            .stream()
            .map(OrderPreview::buildFromEntity)
            .collect(Collectors.toList());
    }
}