package com.ruban.rbac.domain.authz;

import java.util.Date;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 该表纪录系统可登录的用户账号。<br />
 * 每个用户账号依据状态做出是否允许登录的判断。<br />
 * 也可能是系统管理员，也可能不对应某个具体的人，只是一个用户而已,例如：admin
 * 
 * @author ruban
 *
 */
public class User extends PersistentObject {

    /** 用户名 **/
    private String username;

    /** 密码 **/
    private String password;

    /** 昵称 **/
    private String nick;

    /** 状态 **/
    private int state;

    /** 备注 **/
    private String memo;

    /** 最后登录时间 **/
    private Date lastLoginTime;

    /** 所属人员 **/
    private Long personId;

    /** 所属人员名称 **/
    private String personName;

    /** 所属用户组 **/
    private Group group;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}