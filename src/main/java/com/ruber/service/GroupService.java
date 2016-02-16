package com.ruber.service;

import com.ruber.controller.dto.Group;
import com.ruber.controller.dto.GroupsResponse;
import com.ruber.service.vk.VkService;
import com.ruber.service.vk.dto.GetGroupsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private AuthService authService;

    @Autowired
    private VkService vkService;

    public GroupsResponse getGroups(String accessToken, Integer count, Integer offset) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        GetGroupsResponse groupsResponse = vkService.getGroups(count, offset, vkAccessToken);

        return new GroupsResponse(groupsResponse.getTotalCount(), groupsResponse.getGroups());
    }
}