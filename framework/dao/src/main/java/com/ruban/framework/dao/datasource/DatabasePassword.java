package com.ruban.framework.dao.datasource;

import org.springframework.util.Base64Utils;

/**
 * 数据源，用户名，密码加密
 * 
 * @author ruban
 *
 */
public class DatabasePassword {
    /** 用户名 **/
    private String username;
    /** 密码加密 **/
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return deEncryptUsername(this.username);
    }

    public String getPassword() {
        return deEncryptPassword(this.password);
    }

    private String deEncryptUsername(String originalUsername) {
        return decode(originalUsername);
    }

    private String deEncryptPassword(String originalPassword) {
        return decode(originalPassword);
    }

    private static String decode(String originalString) {
        return new String(Base64Utils.decodeFromString(originalString));
    }
}