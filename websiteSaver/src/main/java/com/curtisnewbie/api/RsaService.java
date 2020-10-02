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
     * Get public key string
     *
     * @return
     */
    String getPubKeyStr();

    /**
     * Get public key in bytes
     *
     * @return
     */
    byte[] getPubKey();

    /**
     * Get private key in bytes
     *
     * @return
     */
    byte[] getPrivKey();

    /**
     * Get private key string
     *
     * @return
     */
    String getPrivKeyStr();

    /**
     * Get {@code PrivateKey} for decryption
     *
     * @return
     */
    PrivateKey getPrivateKey();
}
