package com.ruber.service;

import com.ruber.controller.dto.GetGroupsResponse;
import com.ruber.service.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private AuthService authService;

    @Autowired
    private VkService vkService;

    public GetGroupsResponse getGroups(String accessToken, Integer count, Integer offset) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        if (count == null)
            count = 10;
        if (offset == null)
            offset = 0;

        com.ruber.service.vk.dto.GetGroupsResponse getGroupsResponse = vkService.getGroups(count, offset, vkAccessToken);

        return new GetGroupsResponse(getGroupsResponse.getCount(), getGroupsResponse.getGroups());
    }
}