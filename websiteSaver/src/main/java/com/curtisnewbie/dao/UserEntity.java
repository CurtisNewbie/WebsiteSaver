package com.curtisnewbie.dao;

/**
 * @author yongjie.zhuang
 */
public class UserEntity {

    private String username;

    private String password;

    private String salt;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
