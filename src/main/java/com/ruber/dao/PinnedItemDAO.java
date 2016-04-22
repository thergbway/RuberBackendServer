package com.ruber.dao;

import com.ruber.dao.entity.PinnedItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PinnedItemDAO extends GenericDAO<PinnedItem> {
    @Override
    protected Class<PinnedItem> getEntityType() {
        return PinnedItem.class;
    }

    public void deleteById(Integer pinnedItemId) {
        entityManager//it can be avoid if I fix transactions
            .createNamedQuery("PinnedItem.deleteById")
            .setParameter("pinnedItemId", pinnedItemId)
            .executeUpdate();
    }
}
