package com.curtisnewbie.services.api;

import com.curtisnewbie.exception.DecryptionFailureException;

/**
 * <p>
 * Service that decrypts cipher text
 * </p>
 *
 * @author zhuangyongj
 */
public interface RsaDecryptionService {

    /**
     * Decrypt data
     *
     * @param data
     * @return
     * @throws DecryptionFailureException
     */
    String decrypt(byte[] data) throws DecryptionFailureException;

    /**
     * Decrypt data
     * @param data
     * @return
     * @throws DecryptionFailureException
     */
    String decrypt(String data) throws DecryptionFailureException;
}
