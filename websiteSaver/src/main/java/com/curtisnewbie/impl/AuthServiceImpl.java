package com.curtisnewbie.impl;

import com.curtisnewbie.api.AuthService;
import com.curtisnewbie.api.RsaDecryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${authKey}")
    private String authKey;

    @Override
    public boolean isAuthenticated(String authStr) {
        int index = authStr.indexOf(DELIMITER);
        if (index == -1 || index == 0 || index == authStr.length() - 1) {
            logger.error("Invalid key, not authenticated");
            return false;
        }
        long authTime = Long.parseLong(authStr.substring(0, index));
        if (new Date().getTime() - authTime >= 1000) { // only valid for one second
            logger.error("Key expired, not authenticated");
            return false;
        }
        String password = authStr.substring(index + DELIMITER.length());
        boolean isAuthenticated = authKey.equals(password);
        logger.info("Parsed password: {}, is authenticated: {}", password, isAuthenticated);
        return isAuthenticated;
    }
}
