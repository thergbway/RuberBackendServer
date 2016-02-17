package com.ruber.service.vk.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.controller.dto.Group;
import com.ruber.controller.dto.Market;
import com.ruber.service.vk.VkException;
import com.ruber.service.vk.dto.GetGroupsResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class GetGroupsCommand implements VkCommand<GetGroupsResponse> {
    private final Map<String, Object> params = new HashMap<>();

    public GetGroupsCommand(Integer count, Integer offset, String vkAccessToken) {
        params.put("count", count);
        params.put("offset", offset);
        params.put("access_token", vkAccessToken);
    }

    @Override
    public GetGroupsResponse execute() throws VkException {
        try {
            return new RestTemplate().execute("https://api.vk.com/method/execute.getGroupsWithMarketItemsCount?" +
                    "count={count}&offset={offset}&access_token={access_token}&v=5.45",
                HttpMethod.GET, null, response -> {
                    GetGroupsResponse getGroupsResponse = new GetGroupsResponse();

                    JsonNode responseNode = new ObjectMapper().readTree(response.getBody()).get("response");
                    int count = responseNode.get("count").asInt();
                    getGroupsResponse.setCount(count);

                    JsonNode groupsNode = responseNode.get("groups");
                    List<Group> groups = new LinkedList<>();
                    for (JsonNode next : groupsNode) {
                        Group group = new Group();
                        group.setId(next.get("group").get("id").asInt());
                        group.setName(next.get("group").get("name").asText());

                        Market market = new Market();
                        if (next.get("group").get("market").get("enabled").asInt() == 1) {
                            market.setEnabled(true);
                            market.setItems_count(next.get("market_items").asInt());
                        } else {
                            market.setEnabled(false);
                        }
                        group.setMarket(market);

                        groups.add(group);
                    }
                    getGroupsResponse.setGroups(groups.toArray(new Group[0]));

                    return getGroupsResponse;
                }, params);
        } catch (Exception e) {
            throw new VkException(e);
        }
    }
}