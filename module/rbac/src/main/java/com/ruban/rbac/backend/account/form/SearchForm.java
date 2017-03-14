package com.ruban.rbac.backend.account.form;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.rbac.domain.authz.Account;

/**
 * 账号：查询条件
 * 
 * @author ruban
 *
 */
public class SearchForm extends Condition<Account> {

    private static final long serialVersionUID = 6438864561965004773L;

    private String code;

    private String name;

    private Long personId;

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

}
