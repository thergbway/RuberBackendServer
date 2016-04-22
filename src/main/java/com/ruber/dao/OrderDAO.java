package com.ruber.dao;

import com.ruber.dao.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderDAO extends GenericDAO<Order> {
    @Override
    protected Class<Order> getEntityType() {
        return Order.class;
    }
}