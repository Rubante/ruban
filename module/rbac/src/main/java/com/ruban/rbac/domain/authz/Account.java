package com.ruban.rbac.domain.authz;

import java.util.Date;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 该表纪录系统可登录的账号。<br />
 * 每个账号依据状态做出是否允许登录的判断。<br />
 * 也可能是系统管理员，也可能不对应某个具体的人，只是一个用户而已,例如：admin
 * 
 * @author ruban
 *
 */
public class Account extends PersistentObject {

    /** 登录账号 **/
    private String accountNo;

    /** 登录密码 **/
    private String password;

    /** 显示名 **/
    private String name;

    /** 状态 **/
    private String state;

    /** 备注 **/
    private String memo;

    /** 最后登录时间 **/
    private Date lastLoginTime;

    /** 所属人员 **/
    private Long personId;

    /** 所属人员名称 **/
    private String personName;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

}