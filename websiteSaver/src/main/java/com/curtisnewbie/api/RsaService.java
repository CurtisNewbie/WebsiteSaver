package com.curtisnewbie.api;

/**
 * <p>
 * Service for RSA encryption
 * </p>
 * @author zhuangyongj
 */
public interface RsaService {

    /**
     * Get public key
     *
     * @return
     */
    String getPublicKey();

    /**
     * Get private key
     *
     * @return
     */
    String getPrivateKey();
}
