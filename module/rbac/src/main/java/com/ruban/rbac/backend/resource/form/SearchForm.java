package com.ruban.rbac.backend.resource.form;

import com.ruban.common.domain.Resource;
import com.ruban.framework.dao.helper.Condition;

/**
 * 资源：查询条件
 * 
 * @author yjwang
 *
 */
public class SearchForm extends Condition<Resource> {

    private static final long serialVersionUID = 6438864561965004773L;

    private String type;

    private String name;

    private String flag;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
