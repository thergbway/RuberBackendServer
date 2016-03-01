package com.ruber.service.vk.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.service.vk.VkException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditGroupCommand implements VkCommand<Void> {
    private final Map<String, Object> params = new HashMap<>();

    public EditGroupCommand(Integer groupId, Integer market, Integer messages, String vkAccessToken) {
        params.put("access_token", vkAccessToken);
        params.put("group_id", groupId);
        params.put("market", market);
        params.put("messages", messages);
    }

    @Override
    public Void execute() throws VkException {
        try {
            String response = new RestTemplate().getForObject("https://api.vk.com/method/groups.edit?" +
                    "group_id={group_id}&access_token={access_token}&market={market}&messages={messages}&v=5.45",
                String.class, params);

            int result = new ObjectMapper().readTree(response).get("response").asInt();

            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
