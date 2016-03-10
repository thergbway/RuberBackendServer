package com.ruber.dao;

import com.ruber.dao.entity.RuberToken;

public interface RuberTokenDAO extends GenericDAO<RuberToken> {
    RuberToken getByVkToken(String vkToken);
}