package com.ruber.dao;

import com.ruber.dao.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Repository
public class OrdersDAOImpl implements OrdersDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false
    )
    public void create(Order e) {
        entityManager.persist(e);
    }

    @Override
    @Transactional(
        propagation = REQUIRED,
        isolation = READ_COMMITTED,
        readOnly = true
    )
    public Order read(Integer id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false
    )
    public void update(Order e) {
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
}