package com.ruber.dao.impl;

import com.ruber.dao.VkTokenDAO;
import com.ruber.dao.entity.VkToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VkTokenDAOImpl implements VkTokenDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public VkToken getByValue(String tokenValue) {
        List<VkToken> vkTokens = entityManager
            .createNamedQuery("VkToken.getByValue", VkToken.class)
            .setParameter("value", tokenValue)
            .getResultList();

        return vkTokens.size() == 1 ? vkTokens.get(0) : null;
    }

    @Override
    public void create(VkToken e) {
        entityManager.persist(e);
    }

    @Override
    public VkToken read(Integer id) {
        return entityManager.find(VkToken.class, id);
    }

    @Override
    public void update(VkToken e) {
        entityManager.refresh(e);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(VkToken.class, id));
    }
}
