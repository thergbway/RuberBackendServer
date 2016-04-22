package com.ruber.dao;

import com.ruber.dao.entity.VkToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class VkTokenDAO extends GenericDAO<VkToken> {
    @Override
    protected Class<VkToken> getEntityType() {
        return VkToken.class;
    }

    public VkToken getByValue(String tokenValue) {
        List<VkToken> vkTokens = entityManager
            .createNamedQuery("VkToken.getByValue", VkToken.class)
            .setParameter("value", tokenValue)
            .getResultList();

        return vkTokens.size() == 1 ? vkTokens.get(0) : null;
    }
}
