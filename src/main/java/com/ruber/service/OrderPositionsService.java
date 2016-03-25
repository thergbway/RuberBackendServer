package com.ruber.service;

import com.ruber.controller.dto.ItemReplica;
import com.ruber.controller.dto.VkItemReplica;
import com.ruber.dao.OrderPositionDAO;
import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.OrderPosition;
import com.ruber.dao.entity.User;
import com.ruber.exception.InvalidAccessTokenException;
import com.ruber.exception.NoSuchOrderException;
import com.ruber.exception.NoSuchOrderPositionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional// fixme
public class OrderPositionsService {
    @Autowired
    private RuberTokensService ruberTokensService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrderPositionDAO orderPositionDAO;

    public List<ItemReplica> getItemReplicas(String accessToken, Integer orderId) {
        //fixme check whether this order belongs to authenticated user

        List<OrderPosition> orderPositions = getOrderPositions(accessToken, orderId);

        return orderPositions
            .stream()
            .filter(pos -> pos.getClass().equals(com.ruber.dao.entity.ItemReplica.class))
            .map(pos -> ItemReplica.buildFromEntity((com.ruber.dao.entity.ItemReplica) pos))
            .collect(Collectors.toList());
    }

    public List<VkItemReplica> getVkItemReplicas(String accessToken, Integer orderId) {
        //fixme check whether this order belongs to authenticated user

        List<OrderPosition> orderPositions = getOrderPositions(accessToken, orderId);

        return orderPositions
            .stream()
            .filter(pos -> pos.getClass().equals(com.ruber.dao.entity.VkItemReplica.class))
            .map(pos -> VkItemReplica.buildFromEntity((com.ruber.dao.entity.VkItemReplica) pos))
            .collect(Collectors.toList());
    }

    public ItemReplica getItemReplica(String accessToken, Integer orderId, Integer itemId) {
        //fixme check whether this order belongs to authenticated user
        return ItemReplica
            .buildFromEntity((com.ruber.dao.entity.ItemReplica) getOrderPosition(accessToken, orderId, itemId));
    }

    public VkItemReplica getVkItemReplica(String accessToken, Integer orderId, Integer itemId) {
        //fixme check whether this order belongs to authenticated user
        return VkItemReplica
            .buildFromEntity((com.ruber.dao.entity.VkItemReplica) getOrderPosition(accessToken, orderId, itemId));
    }

    public void deleteOrderPosition(String accessToken, Integer orderId, Integer positionId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        List<OrderPosition> orderPositions = order
            .getOrderPositions()
            .stream()
            .filter(position -> position.getId().equals(positionId))
            .collect(Collectors.toList());

        if (orderPositions.size() == 0)
            throw new NoSuchOrderPositionException(positionId);

        orderPositionDAO.deleteById(positionId);
    }

    public Integer addItemReplica(String accessToken, Integer orderId, ItemReplica itemReplica) {
        return addOrderPosition(accessToken, orderId, itemReplica.toEntity());
    }

    public Integer addVkItemReplica(String accessToken, Integer orderId, VkItemReplica vkItemReplica) {
        return addOrderPosition(accessToken, orderId, vkItemReplica.toEntity());
    }

    private Integer addOrderPosition(String accessToken, Integer orderId, OrderPosition position) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        order.getOrderPositions().add(position);

        orderPositionDAO.create(position);

        return position.getId();
    }

    private OrderPosition getOrderPosition(String accessToken, Integer orderId, Integer positionId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        List<OrderPosition> orderPositions = order
            .getOrderPositions()
            .stream()
            .filter(position -> position.getId().equals(positionId))
            .collect(Collectors.toList());

        if (orderPositions.size() != 1)
            throw new NoSuchOrderPositionException(positionId);

        return orderPositions.get(0);
    }

    private List<OrderPosition> getOrderPositions(String accessToken, Integer orderId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        return order.getOrderPositions();
    }
}