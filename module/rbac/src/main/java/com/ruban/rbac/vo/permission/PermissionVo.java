package com.ruban.rbac.vo.permission;

import java.util.List;

import com.ruban.rbac.domain.authz.RolePermission;

public class PermissionVo extends RolePermission {

    private static final long serialVersionUID = 1L;

    /** 可授权的权限树 **/
    private List<String> resources;

    /** 是否可授权给别人 **/
    private List<String> flags;

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public List<String> getFlags() {
        return flags;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

}
