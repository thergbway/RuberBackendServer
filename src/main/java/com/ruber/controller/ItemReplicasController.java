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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(ItemReplicasController.PATH)
public class ItemReplicasController {
    public static final String PATH = "/orders/{orderId}/item_replicas";

    @Autowired
    private OrderPositionsService orderPositionsService;

    @ModelAttribute("user_id")
    public Integer getUserId(HttpServletRequest request) {//fixme this method is presented in about all controllers. Use hierarchy for writing it only once
        return ((Integer) request.getAttribute("user_id"));
    }

    @RequestMapping
    public List<ItemReplica> getItemReplicas(
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("orderId") Integer orderId
    ) {
        return orderPositionsService.getItemReplicas(userId, orderId);
    }

    @RequestMapping("/{itemId}")
    public ItemReplica getItemReplica(
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("itemId") Integer itemId
    ) {
        return orderPositionsService.getItemReplica(userId, orderId, itemId);
    }

    @RequestMapping(value = "/{itemId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemReplica(
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("itemId") Integer itemId
    ) {
        orderPositionsService.deleteOrderPosition(userId, orderId, itemId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> addItemReplica(
        @ModelAttribute("user_id") Integer userId,
        @PathVariable("orderId") Integer orderId,
        @RequestBody(required = true) ItemReplica itemReplica,

        UriComponentsBuilder builder
    ) {
        Integer itemId = orderPositionsService.addItemReplica(userId, orderId, itemReplica);

        UriComponents uriComponents = builder
            .path(PATH + "/{itemId}")
            .buildAndExpand(orderId, itemId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}