package com.ruber.controller;

import com.ruber.controller.dto.GetItemResponse;
import com.ruber.controller.dto.GetItemsResponse;
import com.ruber.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    private ItemsService itemsService;

    @RequestMapping(method = RequestMethod.GET)
    public GetItemsResponse getItems(
        @RequestParam(value = "owner_id", required = true) Integer owner_id,
        @RequestParam(value = "count", required = false) Integer count,
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "access_token", required = true) String access_token) {

        return itemsService.getItems(owner_id, count, offset, access_token);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GetItemResponse getItem(
        @RequestParam(value = "owner_id", required = true) Integer ownerId,
        @PathVariable("id") Integer id,
        @RequestParam(value = "access_token", required = true) String accessToken) {

        return itemsService.getItem(ownerId, id, accessToken);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(
        @RequestParam(value = "owner_id", required = true) Integer ownerId,
        @PathVariable("id") Integer id,
        @RequestParam(value = "access_token", required = true) String accessToken) {

        itemsService.deleteMarketItem(ownerId, id, accessToken);
    }

//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void test(
//        @RequestParam("") String addItemRequestAsString,
//        @RequestPart("file") MultipartFile file) throws IOException {
//
//
//        System.out.println(testJson);
//        String s = IOUtils.toString(file.getInputStream());
//        System.out.println(s);
//
//        return new TestJson("sdsgdrh", 4433416);
//    }
}

