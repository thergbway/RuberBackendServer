package com.ruber.dao;

import com.ruber.dao.entity.PinnedItem;

public interface PinnedItemDAO extends GenericDAO<PinnedItem> {
    void deleteById(Integer pinnedItemId);
}
