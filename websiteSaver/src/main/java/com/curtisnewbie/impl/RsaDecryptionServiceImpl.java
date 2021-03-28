package com.curtisnewbie.impl;

import com.curtisnewbie.api.RsaDecryptionService;
import com.curtisnewbie.api.RsaService;
import com.curtisnewbie.exception.DecryptionFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author zhuangyongj
 */
@Service
public class RsaDecryptionServiceImpl implements RsaDecryptionService {

    private static final Logger logger = LoggerFactory.getLogger(RsaDecryptionServiceImpl.class);
    private static final String RSA = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    @Autowired
    private RsaService rsaService;

    @Override
    public String decrypt(byte[] data) throws DecryptionFailureException {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, rsaService.getPrivateKey());
            return new String(cipher.doFinal(data));
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            logger.error(e.getMessage());
            throw new DecryptionFailureException(e);
        }
    }

    @Override
    public String decrypt(String data) throws DecryptionFailureException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()));
    }
}
