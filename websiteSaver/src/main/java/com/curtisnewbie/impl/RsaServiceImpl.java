package com.curtisnewbie.impl;

import com.curtisnewbie.api.RsaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author zhuangyongj
 */
@Service
public class RsaServiceImpl implements RsaService {
    private static final Logger logger = LoggerFactory.getLogger(RsaServiceImpl.class);
    private static final int KEY_SIZE = 2048;
    private static final String RSA = "RSA";

    private final String pubKeyStr;
    private final String priKeyStr;
    private final Key publicKey;
    private final Key privateKey;

    RsaServiceImpl() throws NoSuchAlgorithmException {
        // only generate key pair for once
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
        KeyPair kp = kpg.generateKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
        pubKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        priKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());

    }

    @Override
    public String getPublicKeyStr() {
        return pubKeyStr;
    }

    @Override
    public byte[] getPublicKey() {
        return publicKey.getEncoded();
    }

    @Override
    public byte[] getPrivateKey() {
        return privateKey.getEncoded();
    }

    @Override
    public String getPrivateKeyStr() {
        return priKeyStr;
    }
}
