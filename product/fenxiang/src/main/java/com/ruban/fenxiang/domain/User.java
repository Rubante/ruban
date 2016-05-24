package com.ruban.fenxiang.domain;

import org.springframework.data.annotation.Id;

/**
 * 登录用户
 * 
 * @author yjwang
 *
 */
public class User {

    @Id
    private String id;

    /**
     * 登录账号
     * 
     */
    private String userno;

    /**
     * 密码
     * 
     */
    private String password;

    /**
     * 昵称
     * 
     */
    private String nick;

    /**
     * 头像
     * 
     */
    private String avatar;

    /**
     * 姓名
     * 
     */
    private String name;

    /**
     * 住址
     * 
     */
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
