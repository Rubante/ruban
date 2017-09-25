package com.ruban.rbac.vo.role;

import java.util.Arrays;
import java.util.List;

import com.ruban.rbac.domain.authz.Role;

public class RoleVo extends Role {

    /** 可授权的权限树 **/
    private List<String> resources;

    /** 是否可授权给别人 **/
    private List<String> flags;

    /** id字符串 **/
    private String resourceIds;

    public List<String> getResources() {
        resources = Arrays.asList(resourceIds.split(","));
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

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

}
