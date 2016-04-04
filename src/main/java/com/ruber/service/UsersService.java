package com.ruber.service;

import com.ruber.dao.UserDAO;
import com.ruber.dao.VkTokenDAO;
import com.ruber.dao.entity.RuberToken;
import com.ruber.dao.entity.User;
import com.ruber.dao.entity.VkToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional//TODO
public class UsersService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private VkTokenDAO vkTokenDAO;

    public boolean isUserExist(Integer vkId) {
        return userDAO.getByVkId(vkId) != null;
    }

    public void addUser(Integer vkId, String vkToken, String ruberToken) {
        User user = new User(null, vkId, Collections.emptySet(), Collections.singletonList(new RuberToken(null, ruberToken)),
            Collections.singletonList(new VkToken(null, vkToken)), Collections.emptyList());

        userDAO.create(user);
    }

    public void addVkTokenToUser(Integer vkId, String vkToken) {
        User user = userDAO.getByVkId(vkId);
        if (vkTokenDAO.getByValue(vkToken) == null)
            user.getVkTokens().add(new VkToken(null, vkToken));
    }

    public void addRuberTokenToUser(Integer vkId, String ruberToken) {
        User user = userDAO.getByVkId(vkId);
        user.getRuberTokens().add(new RuberToken(null, ruberToken));
    }

    public Set<Integer> getConnectedVkGroupIds(Integer userId) {
        User user = userDAO.read(userId);

        return new HashSet<>(user.getConnectedVkGroupIds());//fixme why should we copy all elements(tip: lazy init and transactions)?
    }

    public void addConnectedVkGroupId(Integer userId, Integer vkGroupId) {
        User user = userDAO.read(userId);

        user.getConnectedVkGroupIds().add(vkGroupId);
    }

    public void deleteConnectedVkGroupId(Integer userId, Integer vkGroupId) {
        User user = userDAO.read(userId);

        user.getConnectedVkGroupIds().remove(vkGroupId);
    }
}