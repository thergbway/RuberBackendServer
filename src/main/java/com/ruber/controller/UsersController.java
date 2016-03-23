package com.ruber.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruber.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
//@RequestMapping //todo add right roots later
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping("/connected_groups")
    public Set<Integer> getConnectedVkGroupIds(
        @RequestParam("access_token") String accessToken) {

        return usersService.getConnectedVkGroupIds(accessToken);
    }

    @RequestMapping(value = "connected_groups", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addConnectedVkGroupId(
        @RequestParam("access_token") String accessToken,
        @RequestBody JsonNode requestBody
    ) {
        if (!requestBody.has("vk_group_id") || requestBody.get("vk_group_id").isNull())
            throw new RuntimeException("invalid json");
        else
            usersService.addConnectedVkGroupId(accessToken, requestBody.get("vk_group_id").asInt());
    }

    @RequestMapping(value = "/connected_groups/{group_id}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConnectedVkGroupId(
        @RequestParam("access_token") String accessToken,
        @PathVariable("group_id") Integer vkGroupId
    ) {
        usersService.deleteConnectedVkGroupId(accessToken, vkGroupId);
    }
}