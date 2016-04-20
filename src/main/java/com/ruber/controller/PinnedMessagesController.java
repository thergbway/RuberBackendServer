package com.ruber.controller;

import com.ruber.controller.dto.PinnedMessage;
import com.ruber.service.PinnedItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(PinnedMessagesController.PATH)
public class PinnedMessagesController {
    public static final String PATH = "/markets/{market_vk_id}/orders/{orderId}/pinned_messages";

    @Autowired
    private PinnedItemsService pinnedItemsService;

    @RequestMapping(method = GET)
    public List<PinnedMessage> getPinnedMessages(
        @PathVariable("orderId") Integer orderId,
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        return pinnedItemsService.getPinnedMessages(userId, marketVkId, orderId);
    }

    @RequestMapping(value = "/{messageId}", method = GET)
    public PinnedMessage getPinnedMessage(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("messageId") Integer messageId,
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        return pinnedItemsService.getPinnedMessage(userId, marketVkId, orderId, messageId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<PinnedMessage> createPinnedMessage(
        @PathVariable("orderId") Integer orderId,
        @RequestBody PinnedMessage pinnedMessageInfo,

        @ModelAttribute("user_id") Integer userId,
        @PathVariable("market_vk_id") Integer marketVkId,

        UriComponentsBuilder builder
    ) {
        Integer messageId = pinnedItemsService.addPinnedMessage(userId, marketVkId, orderId, pinnedMessageInfo);

        UriComponents uriComponents = builder
            .path(PATH + "/{messageId}")
            .buildAndExpand(marketVkId, orderId, messageId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        PinnedMessage addedPinnedMessage = pinnedItemsService.getPinnedMessage(userId, marketVkId, orderId, messageId);

        return new ResponseEntity<>(addedPinnedMessage, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{messageId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePinnedMessage(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("messageId") Integer messageId,
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        pinnedItemsService.deletePinnedItem(userId, marketVkId, orderId, messageId);
    }
}