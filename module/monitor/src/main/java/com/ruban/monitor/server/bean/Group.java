package com.ruban.monitor.server.bean;

import java.io.Serializable;

/**
 * 用户组
 * 
 * @author yjwang
 *
 */
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 组名 **/
    private String name;

    /** 是否接受短信报警 **/
    private int telState;

    /** 是否email报警 **/
    private int emailState;

    /** 是否微信报警 **/
    private int wechatState;

    /** 是否微信报警 **/
    private int qqState;

    /** 状态：0新建，1：有效，2：接受报警 **/
    private int state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTelState() {
        return telState;
    }

    public void setTelState(int telState) {
        this.telState = telState;
    }

    public int getEmailState() {
        return emailState;
    }

    public void setEmailState(int emailState) {
        this.emailState = emailState;
    }

    public int getWechatState() {
        return wechatState;
    }

    public void setWechatState(int wechatState) {
        this.wechatState = wechatState;
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

}
