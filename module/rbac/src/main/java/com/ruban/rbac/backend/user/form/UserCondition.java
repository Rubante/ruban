package com.ruban.rbac.backend.user.form;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.rbac.domain.authz.User;

/**
 * 账号：查询条件
 * 
 * @author ruban
 *
 */
public class UserCondition extends Condition<User> {

    private static final long serialVersionUID = 6438864561965004773L;

    private String code;

    private String name;

    private Long personId;

    private String userId;

    /** 用户名 ***/
    private String username;

    /** 密码 **/
    private String password;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

}
