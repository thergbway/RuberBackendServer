package com.ruber.controller;

import com.ruber.controller.dto.Group;
import com.ruber.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public Group[] getGroups(@RequestParam(value = "count", required = false) Integer count,
                             @RequestParam(value = "offset", required = false) Integer offset,
                             @RequestParam(value = "accessToken", required = true) String accessToken) {

        return groupService.getGroupsWithMarketItemsCount(accessToken, count, offset);
    }
}