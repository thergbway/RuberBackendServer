package com.ruber.controller;

import com.ruber.controller.dto.ItemReplica;
import com.ruber.service.OrderPositionsService;
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
@RequestMapping(ItemReplicasController.PATH)
public class ItemReplicasController {
    public static final String PATH = "/markets/{market_vk_id}/orders/{orderId}/item_replicas";

    @Autowired
    private OrderPositionsService orderPositionsService;

    @RequestMapping(method = GET)
    public List<ItemReplica> getItemReplicas(
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        return orderPositionsService.getItemReplicas(userId, marketVkId, orderId);
    }

    @RequestMapping(value = "/{itemId}", method = GET)
    public ItemReplica getItemReplica(
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("itemId") Integer itemId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        return orderPositionsService.getItemReplica(userId, marketVkId, orderId, itemId);
    }

    @RequestMapping(value = "/{itemId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemReplica(
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("itemId") Integer itemId,
        @PathVariable("market_vk_id") Integer marketVkId
    ) {
        orderPositionsService.deleteOrderPosition(userId, marketVkId, orderId, itemId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<ItemReplica> addItemReplica(
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("orderId") Integer orderId,
        @RequestBody(required = true) ItemReplica itemReplica,
        @PathVariable("market_vk_id") Integer marketVkId,

        UriComponentsBuilder builder
    ) {
        Integer itemId = orderPositionsService.addItemReplica(userId, marketVkId, orderId, itemReplica);

        UriComponents uriComponents = builder
            .path(PATH + "/{itemId}")
            .buildAndExpand(marketVkId, orderId, itemId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        ItemReplica addedItemReplica = orderPositionsService.getItemReplica(userId, marketVkId, orderId, itemId);

        return new ResponseEntity<>(addedItemReplica, httpHeaders, HttpStatus.CREATED);
    }
}