package com.curtisnewbie.api;

import com.curtisnewbie.exception.DecryptionFailureException;

import java.security.PrivateKey;

/**
 * <p>
 * Service that decrypts cipher text with given private key
 * </p>
 *
 * @author zhuangyongj
 */
public interface RsaDecryptionService {

    /**
     * Decrypt data with given {@code privateKey}
     *
     * @param data
     * @param privateKey
     * @return
     * @throws DecryptionFailureException
     */
    String decrypt(byte[] data, PrivateKey privateKey) throws DecryptionFailureException;

}
