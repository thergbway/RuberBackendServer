package com.ruber.dao.impl;

import com.ruber.dao.RuberTokenDAO;
import com.ruber.dao.entity.RuberToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class RuberTokenDAOImpl implements RuberTokenDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isRuberTokenValueExists(String token) {
        List<RuberToken> tokens = entityManager//TODO
            .createNamedQuery("RuberToken.getByValue", RuberToken.class)
            .setParameter("value", token)
            .getResultList();

        return tokens.size() != 0;
    }

    @Override
    public void create(RuberToken e) {
        entityManager.persist(e);
    }

    @Override
    public RuberToken read(Integer id) {
        return entityManager.find(RuberToken.class, id);
    }

    @Override
    public void update(RuberToken e) {
        entityManager.refresh(e);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(RuberToken.class, id));
    }
}
