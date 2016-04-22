package com.ruber.dao;

import com.ruber.dao.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAO extends GenericDAO<User> {
    @Override
    protected Class<User> getEntityType() {
        return User.class;
    }

    public User getByVkId(Integer vkId) {
        List<User> users = entityManager//TODO
            .createNamedQuery("User.getByVkId", User.class)
            .setParameter("vkId", vkId)
            .getResultList();

        return users.size() == 1 ? users.get(0) : null;
    }

    public User getByRuberToken(String token) {
        List<User> users = entityManager//TODO
            .createNamedQuery("User.getByRuberToken", User.class)
            .setParameter("token", token)
            .getResultList();

        return users.size() == 1 ? users.get(0) : null;
    }
}
