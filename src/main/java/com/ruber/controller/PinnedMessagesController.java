package com.ruber.controller;

import com.ruber.controller.dto.PinnedMessage;
import com.ruber.service.PinnedMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(PinnedMessagesController.PATH)
public class PinnedMessagesController {
    public static final String PATH = "/orders/{orderId}/pinned_messages";

    @Autowired
    private PinnedMessagesService pinnedMessagesService;

    @RequestMapping
    public List<PinnedMessage> getPinnedMessages(
        @RequestParam("access_token") String accessToken,
        @PathVariable("orderId") Integer orderId
    ) {
        return pinnedMessagesService.getPinnedMessages(accessToken, orderId);
    }

    @RequestMapping("/{messageId}")
    public PinnedMessage getPinnedMessage(
        @RequestParam("access_token") String accessToken,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("messageId") Integer messageId
    ) {
        return pinnedMessagesService.getPinnedMessage(accessToken, orderId, messageId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> createPinnedMessage(
        @RequestParam("access_token") String accessToken,
        @PathVariable("orderId") Integer orderId,
        @RequestBody PinnedMessage pinnedMessageInfo,

        UriComponentsBuilder builder
    ) {
        Integer messageId = pinnedMessagesService.createPinnedMessage(accessToken, orderId, pinnedMessageInfo);

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
        @RequestParam("access_token") String accessToken,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("messageId") Integer messageId
    ) {
        pinnedMessagesService.deletePinnedMessage(accessToken, orderId, messageId);
    }
}