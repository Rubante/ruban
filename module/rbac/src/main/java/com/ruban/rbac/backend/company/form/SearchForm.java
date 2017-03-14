package com.ruban.rbac.backend.company.form;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.rbac.domain.organization.Company;

/**
 * 组织机构：查询条件
 * 
 * @author yjwang
 *
 */
public class SearchForm extends Condition<Company> {

    private static final long serialVersionUID = 6438864561965004773L;

    /** 组织机构类型 **/
    private int type;

    private String code;

    private String name;

    /** 父节点 **/
    private Long companyId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

}
