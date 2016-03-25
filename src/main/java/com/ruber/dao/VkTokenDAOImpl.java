package com.ruber.dao;

import com.ruber.dao.entity.VkToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class VkTokenDAOImpl implements VkTokenDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public VkToken getByValue(String tokenValue) {
        return ((VkToken) entityManager
            .createNamedQuery("VkToken.getByValue")
            .setParameter("value", tokenValue)
            .getSingleResult());
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
