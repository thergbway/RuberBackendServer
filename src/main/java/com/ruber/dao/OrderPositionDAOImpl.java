package com.ruber.dao;

import com.ruber.dao.entity.OrderPosition;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class OrderPositionDAOImpl implements OrderPositionDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteById(Integer positionId) {
        entityManager
            .createNamedQuery("OrderPosition.deleteById")
            .setParameter("positionId", positionId)
            .executeUpdate();
    }

    @Override
    public void create(OrderPosition e) {
        entityManager.persist(e);
    }

    @Override
    public OrderPosition read(Integer id) {
        return entityManager.find(OrderPosition.class, id);
    }

    @Override
    public void update(OrderPosition e) {
        entityManager.refresh(e);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(OrderPosition.class, id));
    }
}
