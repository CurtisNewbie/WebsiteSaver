package com.curtisnewbie.api;

/**
 * <p>
 * Service for RSA encryption
 * </p>
 * @author zhuangyongj
 */
public interface RsaService {

    /**
     * Get public key string
     *
     * @return
     */
    String getPublicKeyStr();

    /**
     * Get public key
     * @return
     */
    byte[] getPublicKey();

    /**
     * Get private key
     *
     * @return
     */
    byte[] getPrivateKey();

    /**
     * Get private key string
     * @return
     */
    String getPrivateKeyStr();
}
