package com.curtisnewbie.impl;

import com.curtisnewbie.api.AuthService;
import com.curtisnewbie.api.RsaDecryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhuangyongj
 */
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static final String DELIMITER = "::";

    @Autowired
    private RsaDecryptionService rsaDecryptionService;

    // TODO: find a way to load this auth key
    private String authKey = "1234567";

    @Override
    public boolean isAuthenticated(String authStrs) {
        int index = authStrs.indexOf(DELIMITER);
        if (index == -1 || index == 0 || index == authStrs.length() - 1) {
            logger.error("Invalid key, not authenticated");
            return false;
        }
        long authTime = Long.parseLong(authStrs.substring(0, index));
        if (new Date().getTime() - authTime >= 10000) { // only valid for 10 seconds
            logger.error("Key expired, not authenticated");
            return false;
        }
        String password = authStrs.substring(index + DELIMITER.length());
        logger.info("Parsed password: {}", password);
        return authKey.equals(password);
    }
}
