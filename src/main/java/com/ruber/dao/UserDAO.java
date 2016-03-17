package com.ruber.dao;

import com.ruber.dao.entity.User;

public interface UserDAO extends GenericDAO<User> {
    User getByVkId(Integer vkId);

    User getByRuberToken(String token);
}
