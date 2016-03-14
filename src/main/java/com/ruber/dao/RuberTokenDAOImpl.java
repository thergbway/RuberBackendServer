package com.ruber.dao;

import com.ruber.dao.entity.RuberToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Repository
public class RuberTokenDAOImpl implements RuberTokenDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false
    )
    public void create(RuberToken e) {
        entityManager.persist(e);
    }

    @Override
    @Transactional(
        propagation = REQUIRED,
        isolation = READ_COMMITTED,
        readOnly = true
    )
    public RuberToken read(Integer userId) {
        return entityManager.find(RuberToken.class, userId);
    }

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false
    )
    public void update(RuberToken e) {
        entityManager.refresh(e);
    }

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false
    )
    public void delete(Integer userId) {
        entityManager.remove(read(userId));
    }

    @Override
    @Transactional(
        propagation = REQUIRED,
        isolation = READ_COMMITTED,
        readOnly = true
    )
    public RuberToken getByValue(String ruberToken) {
        List<RuberToken> tokens = entityManager
            .createNamedQuery("RuberToken.getByValue", RuberToken.class)
            .setParameter("ruberToken", ruberToken)
            .setMaxResults(1)
            .getResultList();

        if (tokens == null || tokens.isEmpty())
            return null;
        else
            return tokens.get(0);
    }
}