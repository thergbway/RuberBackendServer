package com.ruber.dao;

import com.ruber.dao.entity.PinnedMessage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PinnedMessageDAOImpl implements PinnedMessageDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(PinnedMessage e) {
        entityManager.persist(e);
    }

    @Override
    public PinnedMessage read(Integer id) {
        return entityManager.find(PinnedMessage.class, id);
    }

    @Override
    public void update(PinnedMessage e) {
        entityManager.refresh(e);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(PinnedMessage.class, id));
    }

    @Override
    public void deleteById(Integer pinnedMessageId) {
        entityManager
            .createNamedQuery("PinnedMessage.deleteById")
            .setParameter("pinnedMessageId", pinnedMessageId)
            .executeUpdate();
    }
}
