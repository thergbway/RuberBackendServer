package com.ruber.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public abstract class GenericDAO<Entity> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected abstract Class<Entity> getEntityType();

    public void create(Entity e) {
        entityManager.persist(e);
    }

    public Entity read(Integer id) {
        return entityManager.find(getEntityType(), id);
    }

    public void update(Entity e) {
        entityManager.refresh(e);
    }

    public void delete(Integer id) {
        entityManager.remove(read(id));
    }
}