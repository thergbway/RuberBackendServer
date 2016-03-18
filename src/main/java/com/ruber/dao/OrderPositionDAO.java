package com.ruber.dao;

import com.ruber.dao.entity.OrderPosition;

public interface OrderPositionDAO extends GenericDAO<OrderPosition> {
    void deleteOrderPosition(Integer positionId);
}