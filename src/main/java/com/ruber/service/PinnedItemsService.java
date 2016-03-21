package com.ruber.service;

import com.ruber.controller.dto.AddPinnedFileRequestPart;
import com.ruber.controller.dto.PinnedFile;
import com.ruber.controller.dto.PinnedMessage;
import com.ruber.controller.dto.PinnedText;
import com.ruber.dao.PinnedItemDAO;
import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.PinnedItem;
import com.ruber.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PinnedItemsService {
    @Autowired
    private RuberTokensService ruberTokensService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PinnedItemDAO pinnedItemDAO;

    public List<PinnedMessage> getPinnedMessages(String accessToken, Integer orderId) {
        return getPinnedItems(accessToken, orderId)
            .stream()
            .filter(pinnedItem -> pinnedItem.getClass().equals(com.ruber.dao.entity.PinnedMessage.class))
            .map(pinnedItem -> PinnedMessage.buildFromEntity(((com.ruber.dao.entity.PinnedMessage) pinnedItem)))
            .collect(Collectors.toList());
    }

    public List<PinnedText> getPinnedTexts(String accessToken, Integer orderId) {
        return getPinnedItems(accessToken, orderId)
            .stream()
            .filter(pinnedItem -> pinnedItem.getClass().equals(com.ruber.dao.entity.PinnedText.class))
            .map(pinnedItem -> PinnedText.buildFromEntity(((com.ruber.dao.entity.PinnedText) pinnedItem)))
            .collect(Collectors.toList());
    }

    public List<PinnedFile> getPinnedFiles(String accessToken, Integer orderId) {
        return getPinnedItems(accessToken, orderId)
            .stream()
            .filter(pinnedItem -> pinnedItem.getClass().equals(com.ruber.dao.entity.PinnedFile.class))
            .map(pinnedItem -> PinnedFile.buildFromEntity(((com.ruber.dao.entity.PinnedFile) pinnedItem)))
            .collect(Collectors.toList());
    }

    private List<PinnedItem> getPinnedItems(String accessToken, Integer orderId) {
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
            .getPinnedItems();
    }

    public PinnedMessage getPinnedMessage(String accessToken, Integer orderId, Integer messageId) {
        return PinnedMessage
            .buildFromEntity((com.ruber.dao.entity.PinnedMessage) getPinnedItem(accessToken, orderId, messageId));
    }

    public PinnedText getPinnedText(String accessToken, Integer orderId, Integer textId) {
        return PinnedText
            .buildFromEntity((com.ruber.dao.entity.PinnedText) getPinnedItem(accessToken, orderId, textId));
    }

    public PinnedFile getPinnedFile(String accessToken, Integer orderId, Integer fileId) {
        return PinnedFile
            .buildFromEntity((com.ruber.dao.entity.PinnedFile) getPinnedItem(accessToken, orderId, fileId));
    }

    private PinnedItem getPinnedItem(String accessToken, Integer orderId, Integer itemId) {
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

        Optional<PinnedItem> itemOptional = order
            .getPinnedItems()
            .stream()
            .filter(pinnedItem -> pinnedItem.getId().equals(itemId))
            .findFirst();

        if (itemOptional.isPresent())
            return itemOptional.get();
        else
            throw new RuntimeException("No such pinned item with id " + itemId);
    }

    public byte[] getPinnedFileContent(String accessToken, Integer orderId, Integer fileId) {
        com.ruber.dao.entity.PinnedFile pinnedFile
            = (com.ruber.dao.entity.PinnedFile) getPinnedItem(accessToken, orderId, fileId);

        return pinnedFile.getContent();
    }

    public String getPinnedFileFilename(String accessToken, Integer orderId, Integer fileId) {
        com.ruber.dao.entity.PinnedFile pinnedFile
            = (com.ruber.dao.entity.PinnedFile) getPinnedItem(accessToken, orderId, fileId);

        return pinnedFile.getFileName();
    }

    public Integer addPinnedMessage(String accessToken, Integer orderId, PinnedMessage pinnedMessageInfo) {
        return addPinnedItem(accessToken, orderId, pinnedMessageInfo.toEntity());
    }

    public Integer addPinnedText(String accessToken, Integer orderId, PinnedText pinnedTextInfo) {
        return addPinnedItem(accessToken, orderId, pinnedTextInfo.toEntity());
    }

    public Integer addPinnedFile(String accessToken, Integer orderId, AddPinnedFileRequestPart fileInfo, byte[] content, String fileName) {
        com.ruber.dao.entity.PinnedFile pinnedFile =
            new com.ruber.dao.entity.PinnedFile(null, fileInfo.getPosition(), content, fileName);
        return addPinnedItem(accessToken, orderId, pinnedFile);
    }

    private Integer addPinnedItem(String accessToken, Integer orderId, PinnedItem pinnedItem) {
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

        order
            .getPinnedItems()
            .add(pinnedItem);

        pinnedItemDAO.create(pinnedItem);

        return pinnedItem.getId();
    }

    public void deletePinnedItem(String accessToken, Integer orderId, Integer itemId) {
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

        Optional<com.ruber.dao.entity.PinnedItem> itemOptional = order
            .getPinnedItems()
            .stream()
            .filter(pinnedItem -> pinnedItem.getId().equals(itemId))
            .findFirst();

        if (!itemOptional.isPresent())
            throw new RuntimeException("No such pinned item with id " + itemId);
        else
            pinnedItemDAO.deleteById(itemOptional.get().getId());
    }
}