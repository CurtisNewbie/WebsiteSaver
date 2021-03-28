package com.curtisnewbie.api;

import java.security.PrivateKey;

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
}
