package com.curtisnewbie.exception;

/**
 * <p>
 * Exception that indicates a decryption failure
 * </p>
 *
 * @author zhuangyongj
 */
public class DecryptionFailureException extends Exception {

    public DecryptionFailureException(String msg) {
        super(msg);
    }

    public DecryptionFailureException(Exception e) {
        super(e);
    }
}
