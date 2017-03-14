package com.ruban.rbac.backend.permission.form;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.rbac.domain.authz.Permission;

/**
 * 权限：查询条件
 * 
 * @author yjwang
 *
 */
public class SearchForm extends Condition<Permission> {

    private static final long serialVersionUID = 6438864561965004773L;

    private String code;

    private String name;

    /** 所属公司 **/
    private Long companyId;

    /** 所属部门 **/
    private Long departmentId;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

}
