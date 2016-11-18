package com.ruban.rbac.organization.form;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.rbac.domain.organization.Company;

/**
 * 查询条件
 * 
 * @author yjwang
 *
 */
public class SearchForm extends Condition<Company> {

    private static final long serialVersionUID = 6438864561965004773L;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
