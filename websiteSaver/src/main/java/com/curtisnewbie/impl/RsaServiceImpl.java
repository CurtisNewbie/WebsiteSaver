package com.curtisnewbie.impl;

import com.curtisnewbie.api.RsaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
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
    private final Key pubKey;
    private final Key privKey;
    private final PrivateKey privateKey;

    protected RsaServiceImpl() throws NoSuchAlgorithmException {
        // only generate key pair for once
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
        KeyPair kp = kpg.generateKeyPair();
        pubKey = kp.getPublic();
        privKey = kp.getPrivate();
        pubKeyStr = Base64.getEncoder().encodeToString(pubKey.getEncoded());
        priKeyStr = Base64.getEncoder().encodeToString(privKey.getEncoded());
        privateKey = createPrivateKey(privKey.getEncoded());
    }

    private PrivateKey createPrivateKey(byte[] privateKey) {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(String.format("Failed to generate private key: %s", e.getMessage()));
        }
    }

    @Override
    public String getPubKeyStr() {
        return pubKeyStr;
    }

    @Override
    public byte[] getPubKey() {
        return pubKey.getEncoded();
    }

    @Override
    public byte[] getPrivKey() {
        return privKey.getEncoded();
    }

    @Override
    public String getPrivKeyStr() {
        return priKeyStr;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
