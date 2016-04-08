package com.ruber.controller;

import com.ruber.controller.dto.PinnedText;
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
@RequestMapping(PinnedTextsController.PATH)
public class PinnedTextsController {
    public static final String PATH = "/orders/{orderId}/pinned_texts";

    @Autowired
    private PinnedItemsService pinnedItemsService;

    @RequestMapping(method = GET)
    public List<PinnedText> getPinnedTexts(
        @PathVariable("orderId") Integer orderId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return pinnedItemsService.getPinnedTexts(userId, orderId);
    }

    @RequestMapping(value = "/{textId}", method = GET)
    public PinnedText getPinnedText(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("textId") Integer textId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return pinnedItemsService.getPinnedText(userId, orderId, textId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<PinnedText> createPinnedText(
        @PathVariable("orderId") Integer orderId,
        @RequestBody PinnedText pinnedTextInfo,

        @ModelAttribute("user_id") Integer userId,

        UriComponentsBuilder builder
    ) {
        Integer textId = pinnedItemsService.addPinnedText(userId, orderId, pinnedTextInfo);

        UriComponents uriComponents = builder
            .path(PATH + "/{textId}")
            .buildAndExpand(orderId, textId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        PinnedText addedPinnedText = pinnedItemsService.getPinnedText(userId, orderId, textId);

        return new ResponseEntity<>(addedPinnedText, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{textId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePinnedText(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("textId") Integer textId,
        @ModelAttribute("user_id") Integer userId
    ) {
        pinnedItemsService.deletePinnedItem(userId, orderId, textId);
    }
}