package com.ruber.dao;

import com.ruber.dao.entity.RuberToken;

public interface RuberTokenDAO extends GenericDAO<RuberToken> {
    boolean isRuberTokenValueExists(String token);
}
