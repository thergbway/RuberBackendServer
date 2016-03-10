package com.ruber.dao;

import com.ruber.dao.entity.RuberToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public RuberToken read(Integer id) {
        return entityManager.find(RuberToken.class, id);
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
    public void delete(Integer id) {
        entityManager.remove(read(id));
    }

    @Override
    @Transactional(
        propagation = REQUIRED,
        isolation = READ_COMMITTED,
        readOnly = true
    )
    public RuberToken getByVkToken(String vkToken) {
        return entityManager
            .createNamedQuery("getByVkToken", RuberToken.class)
            .setParameter("vkToken", vkToken)
            .getSingleResult();
    }
}
