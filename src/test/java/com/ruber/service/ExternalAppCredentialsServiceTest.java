package com.ruber.service;

import com.ruber.dao.ExternalAppCredentialsDAO;
import com.ruber.dao.entity.ExternalAppCredential;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ExternalAppCredentialsServiceTest {
    @Mock
    private ExternalAppCredentialsDAO externalAppCredentialsDAO;

    @InjectMocks
    private ExternalAppCredentialsService externalAppCredentialsService;

    private ExternalAppCredential validExternalAppCredential;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        validExternalAppCredential = new ExternalAppCredential(1, 100, "valid_secret", "");
    }

    @Test
    public void validCredentialsShouldPass() {
        when(externalAppCredentialsDAO.getByAppId(100)).thenReturn(validExternalAppCredential);

        boolean checkResult = externalAppCredentialsService.checkCredentials(validExternalAppCredential.getAppId(),
            validExternalAppCredential.getAppSecret());

        assertTrue(checkResult);
    }

    @Test
    public void notExistingCredentialShouldFail() {
        when(externalAppCredentialsDAO.getByAppId(200)).thenReturn(null);

        boolean falseResult = externalAppCredentialsService.checkCredentials(200, "any_secret");

        assertFalse(falseResult);
    }

    @Test
    public void invalidSecretShouldFail() {
        when(externalAppCredentialsDAO.getByAppId(100)).thenReturn(validExternalAppCredential);

        boolean invalidSecretResult = externalAppCredentialsService.checkCredentials(100, "invalid_secret");

        assertFalse(invalidSecretResult);
    }
}
