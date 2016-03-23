package com.ruber.service;

import com.ruber.dao.UserDAO;
import com.ruber.dao.entity.RuberToken;
import com.ruber.dao.entity.User;
import com.ruber.dao.entity.VkToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional//TODO
public class UsersService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RuberTokensService ruberTokensService;

    public boolean isUserExist(Integer vkId) {
        return userDAO.getByVkId(vkId) != null;
    }

    public void addUser(Integer vkId, String vkToken, String ruberToken) {
        User user = new User(null, vkId, Collections.emptyList(), Collections.singletonList(new RuberToken(null, ruberToken)),
            Collections.singletonList(new VkToken(null, vkToken)), Collections.emptyList());

        userDAO.create(user);
    }

    public void addVkTokenToUser(Integer vkId, String vkToken) {
        User user = userDAO.getByVkId(vkId);
        user.getVkTokens().add(new VkToken(null, vkToken));
    }

    public void addRuberTokenToUser(Integer vkId, String ruberToken) {
        User user = userDAO.getByVkId(vkId);
        user.getRuberTokens().add(new RuberToken(null, ruberToken));
    }

    public List<Integer> getConnectedVkGroupIds(String accessToken) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        return new LinkedList<>(user.getConnectedVkGroupIds());//fixme why should we copy all elements(tip: lazy init and transactions)?
    }

    public void addConnectedVkGroupId(String accessToken, Integer vkGroupId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        user.getConnectedVkGroupIds().add(vkGroupId);
    }

    public void deleteConnectedVkGroupId(String accessToken, Integer vkGroupId) {
        if (!ruberTokensService.isValidToken(accessToken)) {
            throw new RuntimeException("Invalid access token");
        }

        User user = userDAO.getByRuberToken(accessToken);

        user.getConnectedVkGroupIds().remove(vkGroupId);
    }
}