package com.ruber.dao;

import com.ruber.dao.entity.Market;

public interface MarketDAO extends GenericDAO<Market> {
    Market getByVkGroupId(Integer vkGroupId);
}