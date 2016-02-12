package com.ruber.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.ruber.controller.GroupsController.FieldValue;
import com.ruber.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class GroupService {
    @Autowired
    private AuthService authService;

    {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public Group[] getGroups(String accessToken, FieldValue[] fields) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        HttpRequest req = Unirest.get("https://api.vk.com/method/groups.get")
            .queryString("extended", 1)
            .queryString("access_token", vkAccessToken);

        if (fields != null && Arrays.asList(fields).contains(FieldValue.MARKET))
            req.queryString("fields", "market");

        try {
            Group[] groups = req.asObject(Group[].class).getBody();
            return groups;
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
