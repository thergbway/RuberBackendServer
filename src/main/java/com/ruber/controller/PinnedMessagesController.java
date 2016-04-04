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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(PinnedMessagesController.PATH)
public class PinnedMessagesController {
    public static final String PATH = "/orders/{orderId}/pinned_messages";

    @Autowired
    private PinnedItemsService pinnedItemsService;

    @ModelAttribute("user_id")
    public Integer getUserId(HttpServletRequest request) {//fixme this method is presented in about all controllers. Use hierarchy for writing it only once
        return ((Integer) request.getAttribute("user_id"));
    }

    @RequestMapping
    public List<PinnedMessage> getPinnedMessages(
        @PathVariable("orderId") Integer orderId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return pinnedItemsService.getPinnedMessages(userId, orderId);
    }

    @RequestMapping("/{messageId}")
    public PinnedMessage getPinnedMessage(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("messageId") Integer messageId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return pinnedItemsService.getPinnedMessage(userId, orderId, messageId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> createPinnedMessage(
        @PathVariable("orderId") Integer orderId,
        @RequestBody PinnedMessage pinnedMessageInfo,

        @ModelAttribute("user_id") Integer userId,

        UriComponentsBuilder builder
    ) {
        Integer messageId = pinnedItemsService.addPinnedMessage(userId, orderId, pinnedMessageInfo);

        UriComponents uriComponents = builder
            .path(PATH + "/{messageId}")
            .buildAndExpand(orderId, messageId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{messageId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePinnedMessage(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("messageId") Integer messageId,
        @ModelAttribute("user_id") Integer userId
    ) {
        pinnedItemsService.deletePinnedItem(userId, orderId, messageId);
    }
}