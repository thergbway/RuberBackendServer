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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(VkItemReplicasController.PATH)
public class VkItemReplicasController {
    public static final String PATH = "/orders/{orderId}/vk_item_replicas";

    @Autowired
    OrderPositionsService orderPositionsService;

    @ModelAttribute("user_id")
    public Integer getUserId(HttpServletRequest request) {//fixme this method is presented in about all controllers. Use hierarchy for writing it only once
        return ((Integer) request.getAttribute("user_id"));
    }

    @RequestMapping
    public List<VkItemReplica> getVkItemReplicas(
        @PathVariable("orderId") Integer orderId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return orderPositionsService.getVkItemReplicas(userId, orderId);
    }

    @RequestMapping("/{itemId}")
    public VkItemReplica getVkItemReplica(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("itemId") Integer itemId,
        @ModelAttribute("user_id") Integer userId
    ) {
        return orderPositionsService.getVkItemReplica(userId, orderId, itemId);
    }

    @RequestMapping(value = "/{itemId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVkItemReplica(
        @PathVariable("orderId") Integer orderId,
        @PathVariable("itemId") Integer itemId,
        @ModelAttribute("user_id") Integer userId
    ) {
        orderPositionsService.deleteOrderPosition(userId, orderId, itemId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> addVkItemReplica(
        @PathVariable("orderId") Integer orderId,
        @RequestBody(required = true) VkItemReplica itemReplica,

        @ModelAttribute("user_id") Integer userId,

        UriComponentsBuilder builder
    ) {
        Integer itemId = orderPositionsService.addVkItemReplica(userId, orderId, itemReplica);

        UriComponents uriComponents = builder
            .path(PATH + "/{itemId}")
            .buildAndExpand(orderId, itemId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
