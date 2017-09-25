package com.ruban.rbac.backend.role.form;

import com.ruban.rbac.vo.role.RoleVo;

public class RoleForm extends RoleVo {

    /** 用户数 **/
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
