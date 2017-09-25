package com.ruban.monitor.server.bean;

import java.io.Serializable;

/**
 * 系统用户
 * 
 * @author yjwang
 *
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户名 **/
    private String username;

    /** 姓名 **/
    private String name;

    /** 密码 **/
    private String password;

    /** 手机号 **/
    private String tel;

    /** 是否接受短信报警 **/
    private int telState;

    /** 邮箱 **/
    private String email;

    /** 是否email报警 **/
    private int emailState;

    /** 微信 **/
    private String wechat;

    /** 是否微信报警 **/
    private int wechatState;

    /** qq **/
    private String qq;

    /** 是否微信报警 **/
    private int qqState;

    /** 状态：0新建，1：有效，2：接受报警 **/
    private int state;

    /** 是否接受报警 **/
    private int warn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getTelState() {
        return telState;
    }

    public void setTelState(int telState) {
        this.telState = telState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmailState() {
        return emailState;
    }

    public void setEmailState(int emailState) {
        this.emailState = emailState;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public int getWechatState() {
        return wechatState;
    }

    public void setWechatState(int wechatState) {
        this.wechatState = wechatState;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getQqState() {
        return qqState;
    }

    public void setQqState(int qqState) {
        this.qqState = qqState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getWarn() {
        return warn;
    }

    public void setWarn(int warn) {
        this.warn = warn;
    }

}
