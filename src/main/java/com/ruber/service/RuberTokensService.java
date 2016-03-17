package com.ruber.service;

import com.ruber.dao.RuberTokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;

@Service
@Transactional//TODO
public class RuberTokensService {
    @Autowired
    private RuberTokenDAO ruberTokenDAO;

    public boolean isValidToken(String token) {
        return ruberTokenDAO.isRuberTokenValueExists(token);
    }

    public String getNextToken() {
        String hashCandidate;

        try {
            do {
                Long nanoTime = System.nanoTime();

                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = digest.digest(nanoTime.toString().getBytes("UTF-16"));

                hashCandidate = convertByteArrayToHexString(hashedBytes);
            } while (ruberTokenDAO.isRuberTokenValueExists(hashCandidate));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return hashCandidate;
    }

    private String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte currByte : arrayBytes)
            sb.append(Integer.toString((currByte & 0xff) + 0x100, 16)
                .substring(1));
        return sb.toString();
    }
}
