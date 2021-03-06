package com.ruber.dao;

import com.ruber.dao.entity.Market;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

@Repository
@Transactional
public class MarketDAO extends GenericDAO<Market> {
    @Override
    protected Class<Market> getEntityType() {
        return Market.class;
    }

    public Market getByVkGroupId(Integer vkGroupId) {
        List<Market> markets = entityManager
            .createNamedQuery("Market.getByVkGroupId", Market.class)
            .setParameter("vk_group_id", vkGroupId)
            .getResultList();

        return singleResult(markets);
    }
}