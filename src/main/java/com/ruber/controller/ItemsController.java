package com.ruber.controller;

import com.ruber.controller.dto.Item;
import com.ruber.controller.dto.ItemResponse;
import com.ruber.controller.dto.ItemsResponse;
import com.ruber.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    private ItemsService itemsService;

    @RequestMapping(method = RequestMethod.GET)
    public ItemsResponse getItems(
        @RequestParam(value = "owner_id", required = true) Integer owner_id,
        @RequestParam(value = "count", required = false) Integer count,
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "access_token", required = true) String access_token) {

        return itemsService.getItems(owner_id, count, offset, access_token);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ItemResponse getItem(
        @RequestParam(value = "owner_id", required = true) Integer ownerId,
        @PathVariable("id") Integer id,
        @RequestParam(value = "access_token", required = true) String accessToken) {

        return itemsService.getItem(ownerId, id, accessToken);
    }
}
