package com.ruber.service;

import com.ruber.dao.ExternalAppCredentialsDAO;
import com.ruber.dao.entity.ExternalAppCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional//TODO
public class ExternalAppCredentialsService {
    @Autowired
    private ExternalAppCredentialsDAO externalAppCredentialsDAO;

    public boolean checkCredentials(Integer appId, String appSecret) {
        ExternalAppCredential credential = externalAppCredentialsDAO.getByAppId(appId);

        return credential != null && appSecret.equals(credential.getAppSecret());
    }
}