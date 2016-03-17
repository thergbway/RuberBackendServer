package com.ruber.dao;

import com.ruber.dao.entity.ExternalAppCredential;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Repository
public class ExternalAppCredentialsDAOImpl implements ExternalAppCredentialsDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(
        propagation = REQUIRED,
        isolation = READ_COMMITTED,
        readOnly = true)
    public ExternalAppCredential getByAppId(Integer appId) {
        List<ExternalAppCredential> credentials = entityManager//TODO
            .createNamedQuery("ExternalAppCredential.getByAppId", ExternalAppCredential.class)
            .setParameter("appId", appId)
            .getResultList();

        return credentials.size() == 1 ? credentials.get(0) : null;
    }

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false)
    public void create(ExternalAppCredential e) {
        entityManager.persist(e);
    }

    @Override
    @Transactional(
        propagation = REQUIRED,
        isolation = READ_COMMITTED,
        readOnly = true)
    public ExternalAppCredential read(Integer id) {
        return entityManager.find(ExternalAppCredential.class, id);
    }

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false)
    public void update(ExternalAppCredential e) {
        entityManager.refresh(e);
    }

    @Override
    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false)
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(ExternalAppCredential.class, id));
    }
}