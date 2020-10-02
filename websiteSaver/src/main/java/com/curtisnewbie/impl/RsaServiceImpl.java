package com.curtisnewbie.impl;

import com.curtisnewbie.api.RsaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    private final String publicKey;
    private final String privateKey;

    RsaServiceImpl() throws NoSuchAlgorithmException {
        // only generate key pair for once
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
        KeyPair kp = kpg.generateKeyPair();
        Base64.Encoder base64 = Base64.getEncoder();
        publicKey = base64.encodeToString(kp.getPublic().getEncoded());
        privateKey = base64.encodeToString(kp.getPrivate().getEncoded());
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getPrivateKey() {
        return privateKey;
    }
}
