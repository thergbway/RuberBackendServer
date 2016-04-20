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
import com.ruber.exception.NoSuchMarketException;
import com.ruber.exception.NoSuchOrderException;
import com.ruber.exception.NoSuchPinnedItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ruber.util.TimeUtils.getCurrentTimestamp;

@Service
@Transactional
public class PinnedItemsService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PinnedItemDAO pinnedItemDAO;

    @Autowired
    private MarketService marketService;

    public List<PinnedMessage> getPinnedMessages(Integer userId, Integer marketVkId, Integer orderId) {
        return getPinnedItems(userId, marketVkId, orderId)
            .stream()
            .filter(pinnedItem -> pinnedItem.getClass().equals(com.ruber.dao.entity.PinnedMessage.class))
            .map(pinnedItem -> PinnedMessage.buildFromEntity(((com.ruber.dao.entity.PinnedMessage) pinnedItem)))
            .collect(Collectors.toList());
    }

    public List<PinnedText> getPinnedTexts(Integer userId, Integer marketVkId, Integer orderId) {
        return getPinnedItems(userId, marketVkId, orderId)
            .stream()
            .filter(pinnedItem -> pinnedItem.getClass().equals(com.ruber.dao.entity.PinnedText.class))
            .map(pinnedItem -> PinnedText.buildFromEntity(((com.ruber.dao.entity.PinnedText) pinnedItem)))
            .collect(Collectors.toList());
    }

    public List<PinnedFile> getPinnedFiles(Integer userId, Integer marketVkId, Integer orderId) {
        return getPinnedItems(userId, marketVkId, orderId)
            .stream()
            .filter(pinnedItem -> pinnedItem.getClass().equals(com.ruber.dao.entity.PinnedFile.class))
            .map(pinnedItem -> PinnedFile.buildFromEntity(((com.ruber.dao.entity.PinnedFile) pinnedItem)))
            .collect(Collectors.toList());
    }

    private List<PinnedItem> getPinnedItems(Integer userId, Integer marketVkId, Integer orderId) {
        User user = userDAO.read(userId);

        if (!user.getConnectedMarkets().contains(marketService.getMarketByVkGroupId(marketVkId)))
            throw new NoSuchMarketException(marketVkId, user.getVkId());

        List<Order> orders = marketService
            .getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        return order
            .getPinnedItems();
    }

    public PinnedMessage getPinnedMessage(Integer userId, Integer marketVkId, Integer orderId, Integer messageId) {
        return PinnedMessage
            .buildFromEntity((com.ruber.dao.entity.PinnedMessage) getPinnedItem(userId, marketVkId, orderId, messageId));
    }

    public PinnedText getPinnedText(Integer userId, Integer marketVkId, Integer orderId, Integer textId) {
        return PinnedText
            .buildFromEntity((com.ruber.dao.entity.PinnedText) getPinnedItem(userId, marketVkId, orderId, textId));
    }

    public PinnedFile getPinnedFile(Integer userId, Integer marketVkId, Integer orderId, Integer fileId) {
        return PinnedFile
            .buildFromEntity((com.ruber.dao.entity.PinnedFile) getPinnedItem(userId, marketVkId, orderId, fileId));
    }

    private PinnedItem getPinnedItem(Integer userId, Integer marketVkId, Integer orderId, Integer itemId) {
        User user = userDAO.read(userId);

        if (!user.getConnectedMarkets().contains(marketService.getMarketByVkGroupId(marketVkId)))
            throw new NoSuchMarketException(marketVkId, user.getVkId());

        List<Order> orders = marketService
            .getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        Optional<PinnedItem> itemOptional = order
            .getPinnedItems()
            .stream()
            .filter(pinnedItem -> pinnedItem.getId().equals(itemId))
            .findFirst();

        if (itemOptional.isPresent())
            return itemOptional.get();
        else
            throw new NoSuchPinnedItemException(itemId);
    }

    public byte[] getPinnedFileContent(Integer userId, Integer marketVkId, Integer orderId, Integer fileId) {
        com.ruber.dao.entity.PinnedFile pinnedFile
            = (com.ruber.dao.entity.PinnedFile) getPinnedItem(userId, marketVkId, orderId, fileId);

        return pinnedFile.getContent();
    }

    public String getPinnedFileFilename(Integer userId, Integer marketVkId, Integer orderId, Integer fileId) {
        com.ruber.dao.entity.PinnedFile pinnedFile
            = (com.ruber.dao.entity.PinnedFile) getPinnedItem(userId, marketVkId, orderId, fileId);

        return pinnedFile.getFileName();
    }

    public Integer addPinnedMessage(Integer userId, Integer marketVkId, Integer orderId, PinnedMessage pinnedMessageInfo) {
        return addPinnedItem(userId, marketVkId, orderId, pinnedMessageInfo.toEntity(getCurrentTimestamp()));
    }

    public Integer addPinnedText(Integer userId, Integer marketVkId, Integer orderId, PinnedText pinnedTextInfo) {
        return addPinnedItem(userId, marketVkId, orderId, pinnedTextInfo.toEntity(getCurrentTimestamp()));
    }

    public Integer addPinnedFile(Integer userId, Integer marketVkId, Integer orderId, AddPinnedFileRequestPart fileInfo,
                                 byte[] content, String fileName) {
        com.ruber.dao.entity.PinnedFile pinnedFile =
            new com.ruber.dao.entity.PinnedFile(null, fileInfo.getPosition(), getCurrentTimestamp(), content, fileName);
        return addPinnedItem(userId, marketVkId, orderId, pinnedFile);
    }

    private Integer addPinnedItem(Integer userId, Integer marketVkId, Integer orderId, PinnedItem pinnedItem) {
        User user = userDAO.read(userId);

        if (!user.getConnectedMarkets().contains(marketService.getMarketByVkGroupId(marketVkId)))
            throw new NoSuchMarketException(marketVkId, user.getVkId());

        List<Order> orders = marketService
            .getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        order
            .getPinnedItems()
            .add(pinnedItem);

        pinnedItemDAO.create(pinnedItem);

        return pinnedItem.getId();
    }

    public void deletePinnedItem(Integer userId, Integer marketVkId, Integer orderId, Integer itemId) {
        User user = userDAO.read(userId);

        if (!user.getConnectedMarkets().contains(marketService.getMarketByVkGroupId(marketVkId)))
            throw new NoSuchMarketException(marketVkId, user.getVkId());

        List<Order> orders = marketService
            .getMarketByVkGroupId(marketVkId)
            .getOrders()
            .stream()
            .filter(order -> order.getId().equals(orderId))
            .collect(Collectors.toList());

        if (orders.size() == 0)
            throw new NoSuchOrderException(orderId);

        Order order = orders.get(0);

        Optional<com.ruber.dao.entity.PinnedItem> itemOptional = order
            .getPinnedItems()
            .stream()
            .filter(pinnedItem -> pinnedItem.getId().equals(itemId))
            .findFirst();

        if (!itemOptional.isPresent())
            throw new NoSuchPinnedItemException(itemId);
        else
            pinnedItemDAO.deleteById(itemOptional.get().getId());
    }
}