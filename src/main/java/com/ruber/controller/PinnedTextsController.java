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

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(PinnedTextsController.PATH)
public class PinnedTextsController {
    public static final String PATH = "/orders/{orderId}/pinned_texts";

    @Autowired
    private PinnedItemsService pinnedItemsService;

    @RequestMapping
    public List<PinnedText> getPinnedTexts(
        @RequestParam("access_token") String accessToken,
        @PathVariable("orderId") Integer orderId
    ) {
        return pinnedItemsService.getPinnedTexts(accessToken, orderId);
    }

    @RequestMapping("/{textId}")
    public PinnedText getPinnedText(
        @RequestParam("access_token") String accessToken,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("textId") Integer textId
    ) {
        return pinnedItemsService.getPinnedText(accessToken, orderId, textId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> createPinnedText(
        @RequestParam("access_token") String accessToken,
        @PathVariable("orderId") Integer orderId,
        @RequestBody PinnedText pinnedTextInfo,

        UriComponentsBuilder builder
    ) {
        Integer textId = pinnedItemsService.addPinnedText(accessToken, orderId, pinnedTextInfo);

        UriComponents uriComponents = builder
            .path(PATH + "/{textId}")
            .buildAndExpand(orderId, textId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{textId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePinnedText(
        @RequestParam("access_token") String accessToken,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("textId") Integer textId
    ) {
        pinnedItemsService.deletePinnedItem(accessToken, orderId, textId);
    }
}