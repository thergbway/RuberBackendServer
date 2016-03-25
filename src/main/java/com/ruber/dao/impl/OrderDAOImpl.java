package com.ruber.dao.impl;

import com.ruber.dao.OrderDAO;
import com.ruber.dao.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Order e) {
        entityManager.persist(e);
    }

    @Override
    public Order read(Integer id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public void update(Order e) {
        entityManager.refresh(e);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(Order.class, id));
    }
}
