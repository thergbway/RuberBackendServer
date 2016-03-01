package com.ruber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.controller.dto.EditGroupRequest;
import com.ruber.controller.dto.GetGroupsResponse;
import com.ruber.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

@RestController
@RequestMapping("/groups")
public class GroupsController {
    @Autowired
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public GetGroupsResponse getGroups(@RequestParam(value = "count", required = false) Integer count,
                                       @RequestParam(value = "offset", required = false) Integer offset,
                                       @RequestParam(value = "access_token", required = true) String accessToken) {

        return groupService.getGroups(accessToken, count, offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void editGroup(
        @RequestParam(value = "access_token", required = true) String accessToken,
        @PathVariable("id") Integer groupId,
        @RequestBody(required = true) EditGroupRequest groupInfo) {

        groupService.editGroup(groupId, groupInfo, accessToken);
    }
}