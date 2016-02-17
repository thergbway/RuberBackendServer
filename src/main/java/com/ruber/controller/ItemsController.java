package com.ruber.controller;

import com.ruber.controller.dto.ItemsResponse;
import com.ruber.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
