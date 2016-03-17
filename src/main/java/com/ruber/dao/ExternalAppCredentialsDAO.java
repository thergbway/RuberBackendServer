package com.ruber.dao;

import com.ruber.dao.entity.ExternalAppCredential;

public interface ExternalAppCredentialsDAO extends GenericDAO<ExternalAppCredential> {
    ExternalAppCredential getByAppId(Integer appId);
}
