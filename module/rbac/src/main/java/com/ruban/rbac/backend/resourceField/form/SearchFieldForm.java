package com.ruban.rbac.backend.resourceField.form;

import com.ruban.common.domain.ResourceField;
import com.ruban.framework.dao.helper.Condition;

/**
 * 资源属性：查询条件
 * 
 * @author yjwang
 *
 */
public class SearchFieldForm extends Condition<ResourceField> {

    private static final long serialVersionUID = 6438864561965004773L;

    private String code;

    private String name;

    private String resourceId;

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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

}
