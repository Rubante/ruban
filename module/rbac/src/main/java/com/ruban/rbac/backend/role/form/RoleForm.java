package com.ruban.rbac.backend.role.form;

import com.ruban.rbac.vo.role.RoleVo;

public class RoleForm extends RoleVo {

    private static final long serialVersionUID = -8068180251093563415L;

    /** 用户数 **/
    private int count;

    /** 可授权的权限树 **/
    private String permissions;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

}
