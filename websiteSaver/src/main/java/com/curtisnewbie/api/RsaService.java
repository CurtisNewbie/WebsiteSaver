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

    String getPublicKeyStr();

    PrivateKey getPrivateKey();

    PublicKey getPublicKey();
}
