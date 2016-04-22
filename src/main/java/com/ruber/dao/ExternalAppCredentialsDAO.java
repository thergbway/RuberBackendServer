package com.ruber.dao;

import com.ruber.dao.entity.ExternalAppCredential;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

@Repository
@Transactional
public class ExternalAppCredentialsDAO extends GenericDAO<ExternalAppCredential> {
    @Override
    protected Class<ExternalAppCredential> getEntityType() {
        return ExternalAppCredential.class;
    }

    public ExternalAppCredential getByAppId(Integer appId) {
        List<ExternalAppCredential> credentials = entityManager
            .createNamedQuery("ExternalAppCredential.getByAppId", ExternalAppCredential.class)
            .setParameter("appId", appId)
            .getResultList();

        return singleResult(credentials);
    }
}