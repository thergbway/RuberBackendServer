package com.ruber.dao;

import com.ruber.dao.entity.OrderPosition;

public interface OrderPositionDAO extends GenericDAO<OrderPosition> {
    void deleteById(Integer positionId);
}