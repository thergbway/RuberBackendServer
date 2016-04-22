package com.ruber.dao;

import com.ruber.dao.entity.RuberToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RuberTokenDAO extends GenericDAO<RuberToken> {
    @Override
    protected Class<RuberToken> getEntityType() {
        return RuberToken.class;
    }

    public boolean isRuberTokenValueExists(String token) {
        List<RuberToken> tokens = entityManager
            .createNamedQuery("RuberToken.getByValue", RuberToken.class)
            .setParameter("value", token)
            .getResultList();

        return tokens.size() != 0;
    }
}
