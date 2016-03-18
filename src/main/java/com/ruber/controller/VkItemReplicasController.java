package com.ruber.controller;

import com.ruber.controller.dto.VkItemReplica;
import com.ruber.service.OrderPositionsService;
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
@RequestMapping(VkItemReplicasController.PATH)
public class VkItemReplicasController {
    public static final String PATH = "/orders/{orderId}/vk_item_replicas";

    @Autowired
    OrderPositionsService orderPositionsService;

    @RequestMapping
    public List<VkItemReplica> getVkItemReplicas(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @PathVariable("orderId") Integer orderId
    ) {
        return orderPositionsService.getVkItemReplicas(accessToken, orderId);
    }

    @RequestMapping("/{itemId}")
    public VkItemReplica getVkItemReplica(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("itemId") Integer itemId
    ) {
        return orderPositionsService.getVkItemReplica(accessToken, orderId, itemId);
    }

    @RequestMapping(value = "/{itemId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVkItemReplica(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @PathVariable("orderId") Integer orderId,
        @PathVariable("itemId") Integer itemId
    ) {
        orderPositionsService.deleteOrderPosition(accessToken, orderId, itemId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> addVkItemReplica(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @PathVariable("orderId") Integer orderId,
        @RequestBody(required = true) VkItemReplica itemReplica,

        UriComponentsBuilder builder
    ) {
        Integer itemId = orderPositionsService.addVkItemReplica(accessToken, orderId, itemReplica);

        UriComponents uriComponents = builder
            .path(PATH + "/{itemId}")
            .buildAndExpand(orderId, itemId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
