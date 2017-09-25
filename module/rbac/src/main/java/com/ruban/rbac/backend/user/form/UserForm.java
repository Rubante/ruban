package com.ruban.rbac.backend.user.form;

import com.ruban.rbac.vo.user.UserVo;

/**
 * 用户上送数据
 * 
 * @author yjwang
 *
 */
public class UserForm extends UserVo {

    /** 用户Id **/
    private String userId;

    /** 用户所选角色 **/
    private String roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
