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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(PinnedTextsController.PATH)
public class PinnedTextsController {
    public static final String PATH = "/orders/{orderId}/pinned_texts";

    @Autowired
    private PinnedItemsService pinnedItemsService;

    @ModelAttribute("user_id")
    public Integer getUserId(HttpServletRequest request) {//fixme this method is presented in about all controllers. Use hierarchy for writing it only once
        return ((Integer) request.getAttribute("user_id"));
    }

    @RequestMapping
    public List<PinnedText> getPinnedTexts(
        @PathVariable("orderId") Integer orderId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return pinnedItemsService.getPinnedTexts(userId, orderId);
    }

    @RequestMapping("/{textId}")
    public PinnedText getPinnedText(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("textId") Integer textId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return pinnedItemsService.getPinnedText(userId, orderId, textId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> createPinnedText(
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

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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