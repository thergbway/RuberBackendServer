package com.ruber.dao;

import com.ruber.dao.entity.OrderPosition;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderPositionDAO extends GenericDAO<OrderPosition> {
    @Override
    protected Class<OrderPosition> getEntityType() {
        return OrderPosition.class;
    }

    public void deleteById(Integer positionId) {
        entityManager//it can be avoid if I fix transactions
            .createNamedQuery("OrderPosition.deleteById")
            .setParameter("positionId", positionId)
            .executeUpdate();
    }
}