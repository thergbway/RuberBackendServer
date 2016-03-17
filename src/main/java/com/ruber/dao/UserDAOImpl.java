package com.ruber.dao;

import com.ruber.dao.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.DEFAULT;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getByVkId(Integer vkId) {
        List<User> users = entityManager//TODO
            .createNamedQuery("User.getByVkId", User.class)
            .setParameter("vkId", vkId)
            .getResultList();

        return users.size() == 1 ? users.get(0) : null;
    }

    @Override
    public User getByRuberToken(String token) {
        List<User> users = entityManager//TODO
            .createNamedQuery("User.getByRuberToken", User.class)
            .setParameter("token", token)
            .getResultList();

        return users.size() == 1 ? users.get(0) : null;
    }

    @Override
    public void create(User e) {
        entityManager.persist(e);
    }

    @Override
    public User read(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        isolation = DEFAULT
    )
    public void update(User e) {
        entityManager.refresh(e);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(User.class, id));
    }
}
