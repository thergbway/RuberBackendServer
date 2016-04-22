package com.ruber.service;

import com.ruber.controller.dto.ItemReplica;
import com.ruber.dao.OrderPositionDAO;
import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.OrderPosition;
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

    @Autowired
    private MarketService marketService;

    @Autowired
    private UsersService usersService;

    public List<ItemReplica> getItemReplicas(Integer userId, Integer marketVkId, Integer orderId) {
        usersService.assureMarketBelongsToUser(userId, marketVkId);

        Order order = marketService
            .getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(currOrder -> currOrder.getId().equals(orderId))
            .findFirst()
            .orElseThrow(() -> new NoSuchOrderException(orderId));

        return order
            .getOrderPositions()
            .stream()
            .filter(pos -> pos instanceof com.ruber.dao.entity.ItemReplica)
            .map(pos -> ItemReplica.buildFromEntity((com.ruber.dao.entity.ItemReplica) pos))
            .collect(Collectors.toList());
    }

    public ItemReplica getItemReplica(Integer userId, Integer marketVkId, Integer orderId, Integer itemId) {
        usersService.assureMarketBelongsToUser(userId, marketVkId);

        Order order = marketService
            .getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(currOrder -> currOrder.getId().equals(orderId))
            .findFirst()
            .orElseThrow(() -> new NoSuchOrderException(orderId));

        return order
            .getOrderPositions()
            .stream()
            .filter(position -> position.getId().equals(itemId) && position instanceof com.ruber.dao.entity.ItemReplica)
            .map(position -> ((com.ruber.dao.entity.ItemReplica) position))
            .map(ItemReplica::buildFromEntity)
            .findFirst()
            .orElseThrow(() -> new NoSuchOrderPositionException(itemId));
    }

    public void deleteOrderPosition(Integer userId, Integer marketVkId, Integer orderId, Integer positionId) {
        usersService.assureMarketBelongsToUser(userId, marketVkId);

        Order order = marketService
            .getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(currOrder -> currOrder.getId().equals(orderId))
            .findFirst()
            .orElseThrow(() -> new NoSuchOrderException(orderId));

        OrderPosition orderPosition = order
            .getOrderPositions()
            .stream()
            .filter(position -> position.getId().equals(positionId))
            .findFirst()
            .orElseThrow(() -> new NoSuchOrderPositionException(positionId));

        orderPositionDAO.deleteById(orderPosition.getId());
    }

    public Integer addItemReplica(Integer userId, Integer marketVkId, Integer orderId, ItemReplica itemReplica) {
        usersService.assureMarketBelongsToUser(userId, marketVkId);

        Order order = marketService
            .getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(currOrder -> currOrder.getId().equals(orderId))
            .findFirst()
            .orElseThrow(() -> new NoSuchOrderException(orderId));

        com.ruber.dao.entity.ItemReplica itemReplicaEntity = itemReplica.toEntity();

        order
            .getOrderPositions()
            .add(itemReplicaEntity);

        orderPositionDAO.create(itemReplicaEntity);

        return itemReplicaEntity.getId();
    }
}