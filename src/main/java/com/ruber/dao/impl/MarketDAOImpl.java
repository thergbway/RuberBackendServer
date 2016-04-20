package com.ruber.dao.impl;

import com.ruber.dao.MarketDAO;
import com.ruber.dao.entity.Market;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MarketDAOImpl implements MarketDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Market e) {
        entityManager.persist(e);
    }

    @Override
    public Market read(Integer id) {
        return entityManager.find(Market.class, id);
    }

    @Override
    public void update(Market e) {
        entityManager.refresh(e);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(Market.class, id));
    }

    @Override
    public Market getByVkGroupId(Integer vkGroupId) {
        List<Market> markets = entityManager
            .createNamedQuery("Market.getByVkGroupId", Market.class)
            .setParameter("vk_group_id", vkGroupId)
            .getResultList();

        return markets.size() == 1 ? markets.get(0) : null;
    }
}
