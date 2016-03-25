package com.ruber.dao;

import com.ruber.dao.entity.VkToken;

public interface VkTokenDAO extends GenericDAO<VkToken> {
    VkToken getByValue(String tokenValue);
}
