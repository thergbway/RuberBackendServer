package com.ruber.service;

import com.ruber.controller.dto.PinnedMessage;
import com.ruber.dao.PinnedMessageDAO;
import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PinnedMessagesService {
    @Autowired
    private RuberTokensService ruberTokensService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PinnedMessageDAO pinnedMessageDAO;

    public List<PinnedMessage> getPinnedMessages(String accessToken, Integer orderId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new RuntimeException(String.format("Order with id = %d for this user does not exist", orderId));

        Order order = orders.get(0);

        return order
            .getPinnedMessages()
            .stream()
            .map(PinnedMessage::buildFromEntity)
            .collect(Collectors.toList());
    }

    public PinnedMessage getPinnedMessage(String accessToken, Integer orderId, Integer messageId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new RuntimeException(String.format("Order with id = %d for this user does not exist", orderId));

        Order order = orders.get(0);

        Optional<PinnedMessage> messageOptional = order
            .getPinnedMessages()
            .stream()
            .filter(pinnedMessage -> pinnedMessage.getId().equals(messageId))
            .map(PinnedMessage::buildFromEntity)
            .findFirst();

        if (messageOptional.isPresent())
            return messageOptional.get();
        else
            throw new RuntimeException("No such pinned message with id " + messageId);
    }

    public Integer createPinnedMessage(String accessToken, Integer orderId, PinnedMessage pinnedMessageInfo) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new RuntimeException(String.format("Order with id = %d for this user does not exist", orderId));

        Order order = orders.get(0);

        com.ruber.dao.entity.PinnedMessage pinnedMessage = pinnedMessageInfo.toEntity();
        order
            .getPinnedMessages()
            .add(pinnedMessage);

        pinnedMessageDAO.create(pinnedMessage);

        return pinnedMessage.getId();
    }

    public void deletePinnedMessage(String accessToken, Integer orderId, Integer messageId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        List<Order> orders = user
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new RuntimeException(String.format("Order with id = %d for this user does not exist", orderId));

        Order order = orders.get(0);

        Optional<com.ruber.dao.entity.PinnedMessage> messageOptional = order
            .getPinnedMessages()
            .stream()
            .filter(pinnedMessage -> pinnedMessage.getId().equals(messageId))
            .findFirst();

        if (!messageOptional.isPresent())
            throw new RuntimeException("No such pinned message with id " + messageId);
        else
            pinnedMessageDAO.deleteById(messageOptional.get().getId());
    }
}