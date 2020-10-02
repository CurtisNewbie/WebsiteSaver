package com.curtisnewbie.api;

/**
 * <p>
 * Authentication Service
 * </p>
 *
 * @author zhuangyongj
 */
public interface AuthService {

    /**
     * Return whether the authKey is valid
     *
     * @param authKey
     * @return
     */
    boolean isAuthenticated(String authKey);
}
