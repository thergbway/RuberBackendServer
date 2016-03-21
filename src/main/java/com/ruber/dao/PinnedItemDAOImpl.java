package com.ruber.dao;

import com.ruber.dao.entity.PinnedItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PinnedItemDAOImpl implements PinnedItemDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteById(Integer pinnedItemId) {
        entityManager
            .createNamedQuery("PinnedItem.deleteById")
            .setParameter("pinnedItemId", pinnedItemId)
            .executeUpdate();
    }

    @Override
    public void create(PinnedItem e) {
        entityManager.persist(e);
    }

    @Override
    public PinnedItem read(Integer id) {
        return entityManager.find(PinnedItem.class, id);
    }

    @Override
    public void update(PinnedItem e) {
        entityManager.refresh(e);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(PinnedItem.class, id));
    }
}
