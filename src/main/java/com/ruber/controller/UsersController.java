package com.ruber.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruber.exception.InvalidRequestJsonException;
import com.ruber.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
//@RequestMapping //todo add right roots later
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/connected_groups", method = GET)
    public Set<Integer> getConnectedVkGroupIds(@ModelAttribute("user_id") Integer userId) {

        return usersService.getConnectedVkGroupIds(userId);
    }

    @RequestMapping(value = "connected_groups", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Set<Integer> addConnectedVkGroupId(
        @RequestBody JsonNode requestBody,
        @ModelAttribute("user_id") Integer userId
    ) {
        if (!requestBody.has("vk_group_id") || requestBody.get("vk_group_id").isNull())
            throw new InvalidRequestJsonException("vk_group_id is missed or equals null");
        else {
            usersService.addConnectedVkGroupId(userId, requestBody.get("vk_group_id").asInt());

            return usersService.getConnectedVkGroupIds(userId);
        }
    }

    @RequestMapping(value = "/connected_groups/{group_id}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConnectedVkGroupId(
        @PathVariable("group_id") Integer vkGroupId,
        @ModelAttribute("user_id") Integer userId
    ) {
        usersService.deleteConnectedVkGroupId(userId, vkGroupId);
    }
}