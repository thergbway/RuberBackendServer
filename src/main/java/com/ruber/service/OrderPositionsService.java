package com.ruber.service;

import com.ruber.controller.dto.ItemReplica;
import com.ruber.controller.dto.VkItemReplica;
import com.ruber.dao.OrderPositionDAO;
import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.OrderPosition;
import com.ruber.dao.entity.User;
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
    private UserDAO userDAO;

    @Autowired
    private OrderPositionDAO orderPositionDAO;

    public List<ItemReplica> getItemReplicas(Integer userId, Integer orderId) {
        //fixme check whether this order belongs to authenticated user
        List<OrderPosition> orderPositions = getOrderPositions(userId, orderId);

        return orderPositions
            .stream()
            .filter(pos -> pos.getClass().equals(com.ruber.dao.entity.ItemReplica.class))
            .map(pos -> ItemReplica.buildFromEntity((com.ruber.dao.entity.ItemReplica) pos))
            .collect(Collectors.toList());
    }

    public List<VkItemReplica> getVkItemReplicas(Integer userId, Integer orderId) {
        //fixme check whether this order belongs to authenticated user
        List<OrderPosition> orderPositions = getOrderPositions(userId, orderId);

        return orderPositions
            .stream()
            .filter(pos -> pos.getClass().equals(com.ruber.dao.entity.VkItemReplica.class))
            .map(pos -> VkItemReplica.buildFromEntity((com.ruber.dao.entity.VkItemReplica) pos))
            .collect(Collectors.toList());
    }

    public ItemReplica getItemReplica(Integer userId, Integer orderId, Integer itemId) {
        //fixme check whether this order belongs to authenticated user
        return ItemReplica
            .buildFromEntity((com.ruber.dao.entity.ItemReplica) getOrderPosition(userId, orderId, itemId));
    }

    public VkItemReplica getVkItemReplica(Integer userId, Integer orderId, Integer itemId) {
        //fixme check whether this order belongs to authenticated user
        return VkItemReplica
            .buildFromEntity((com.ruber.dao.entity.VkItemReplica) getOrderPosition(userId, orderId, itemId));
    }

    public void deleteOrderPosition(Integer userId, Integer orderId, Integer positionId) {
        User user = userDAO.read(userId);

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

    public Integer addItemReplica(Integer userId, Integer orderId, ItemReplica itemReplica) {
        return addOrderPosition(userId, orderId, itemReplica.toEntity());
    }

    public Integer addVkItemReplica(Integer userId, Integer orderId, VkItemReplica vkItemReplica) {
        return addOrderPosition(userId, orderId, vkItemReplica.toEntity());
    }

    private Integer addOrderPosition(Integer userId, Integer orderId, OrderPosition position) {
        User user = userDAO.read(userId);

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

    private OrderPosition getOrderPosition(Integer userId, Integer orderId, Integer positionId) {
        User user = userDAO.read(userId);

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

    private List<OrderPosition> getOrderPositions(Integer userId, Integer orderId) {
        User user = userDAO.read(userId);

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