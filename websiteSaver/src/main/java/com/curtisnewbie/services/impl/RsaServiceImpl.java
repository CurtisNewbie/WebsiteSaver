package com.curtisnewbie.services.impl;

import com.curtisnewbie.services.api.RsaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Base64;

/**
 * @author zhuangyongj
 */
@Service
public class RsaServiceImpl implements RsaService {
    private static final Logger logger = LoggerFactory.getLogger(RsaServiceImpl.class);
    private static final String RSA = "RSA";

    private final String pubKeyStr;
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public RsaServiceImpl() throws NoSuchAlgorithmException {
        // only generate key pair for once
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
        KeyPair kp = kpg.generateKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
        pubKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    @Override
    public String getPublicKeyStr() {
        return pubKeyStr;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
