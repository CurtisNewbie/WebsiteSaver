package com.curtisnewbie.impl;

import com.curtisnewbie.api.RsaDecryptionService;
import com.curtisnewbie.exception.DecryptionFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/**
 * @author zhuangyongj
 */
@Service
public class RsaDecryptionServiceImpl implements RsaDecryptionService {

    private static final Logger logger = LoggerFactory.getLogger(RsaDecryptionServiceImpl.class);
    private static final String RSA = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    @Override
    public String decrypt(byte[] data, PrivateKey privateKey) throws DecryptionFailureException {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(data));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            logger.error(e.getMessage());
            throw new DecryptionFailureException(e);
        }
    }
}
