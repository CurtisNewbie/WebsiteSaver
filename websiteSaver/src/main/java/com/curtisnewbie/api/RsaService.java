package com.curtisnewbie.api;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * <p>
 * Service for RSA encryption
 * </p>
 *
 * @author zhuangyongj
 */
public interface RsaService {

    /**
     * Get Public Key as a Base64 encoded string
     */
    String getPublicKeyStr();

    /**
     * Get private key
     */
    PrivateKey getPrivateKey();

    /**
     * Get public key
     */
    PublicKey getPublicKey();
}
