package com.curtisnewbie.impl;

import com.curtisnewbie.api.AuthService;
import com.curtisnewbie.api.RsaDecryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Value(value = "${authKey}")
    private String authKey;

    @Value(value = "${shouldUseCustomAuthKey}")
    private boolean shouldUseCustomAuthKey;

    private String generatedAuthKey;

    @PostConstruct
    void init() {
        if (!shouldUseCustomAuthKey) {
            generatedAuthKey = "";
            SecureRandom sr = new SecureRandom();
            List<Character> chars = new ArrayList<>();
            for (int i = 49; i < 58; i++) // numbers
                chars.add((char) i);
            for (int i = 65; i < 90; i++) // uppercase alphabet
                chars.add((char) i);
            for (int i = 97; i < 122; i++) // lowercase alphabet
                chars.add((char) i);
            for (int i = 0; i < 6; i++)
                generatedAuthKey += chars.get(sr.nextInt(chars.size() + 1)); // ASCII chars that can be easily typed
            logger.info("Using generated authKey: '{}'", generatedAuthKey);
        } else {
            logger.info("Using predefined authKey: '{}'", authKey);
        }
    }

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
        boolean isAuthenticated;
        if (shouldUseCustomAuthKey) {
            isAuthenticated = authKey.equals(password);
        } else {
            isAuthenticated = generatedAuthKey.equals(password);
        }
        logger.info("Parsed password: {}, is authenticated: {}", password, isAuthenticated);
        return isAuthenticated;
    }
}
